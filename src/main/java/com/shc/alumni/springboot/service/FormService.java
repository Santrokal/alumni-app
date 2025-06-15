package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.AlumniSettings;
import com.shc.alumni.springboot.entity.FormField;
import com.shc.alumni.springboot.repository.FormRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private AgmMembershipService agmMembershipService;

    @Autowired
    private EntityManager entityManager;

    private static final String[] AVAILABLE_FIELDS = {
        "Name:text", "Email:email", "Address:textarea",
        "Graduation Year:number", "Department:text", "Attendance:checkbox",
        "Gender:select", "Date of Birth:date", "Occupation:text",
        "Company Name:text", "Job Title:text", "Marital Status:select",
        "Spouse Name:text", "Number of Children:number", "LinkedIn URL:text",
        "Comments:textarea",
        "Emergency Contact:text", "Blood Group:text", "Nationality:text"
    };

    public String searchFields(String searchTerm) {
        StringBuilder result = new StringBuilder();
        for (String field : AVAILABLE_FIELDS) {
            String[] parts = field.split(":");
            if (parts[0].toLowerCase().contains(searchTerm.toLowerCase())) {
                result.append("<div>").append(parts[0]).append(" (").append(parts[1])
                    .append(") <button onclick=\"addField('").append(parts[0])
                    .append("','").append(parts[1]).append("')\">Add</button></div>");
            }
        }
        return result.toString();
    }

    @Transactional
    public void createForm(String fieldsData) {
        JSONArray fieldsJson = new JSONArray(fieldsData);
        List<FormField> fields = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();
        StringBuilder fieldsSql = new StringBuilder();

        for (int i = 0; i < fieldsJson.length(); i++) {
            JSONObject fieldJson = fieldsJson.getJSONObject(i);
            String fieldName = fieldJson.getString("name");
            if (fieldNames.add(fieldName)) { // Only add if fieldName is unique
                FormField field = new FormField();
                field.setName(fieldName);
                field.setType(fieldJson.getString("type"));
                field.setTableName("agm_responses");
                fields.add(field);

                String sqlFieldName = fieldName.replace(" ", "_").toLowerCase();
                String sqlType = getSQLType(field.getType());
                fieldsSql.append(sqlFieldName).append(" ").append(sqlType);
                if (i < fieldsJson.length() - 1) {
                    fieldsSql.append(", ");
                }
            }
        }

        formRepository.deleteAll(); // Clear existing fields
        formRepository.saveAll(fields);
        entityManager.createNativeQuery("DROP TABLE IF EXISTS agm_responses").executeUpdate();
        String createQuery = "CREATE TABLE agm_responses (id INT AUTO_INCREMENT PRIMARY KEY, " +
            fieldsSql.toString() + ", phone_number VARCHAR(50))";
        entityManager.createNativeQuery(createQuery).executeUpdate();

        AlumniSettings settings = entityManager.find(AlumniSettings.class, 1L);
        if (settings == null) {
            settings = new AlumniSettings();
            settings.setId(1L);
        }
        settings.setAgmFormActive(true);
        entityManager.merge(settings);
    }

    public List<FormField> getFormFields() {
        return formRepository.findAll();
    }

    @Transactional
    public void saveResponse(String phoneNumber, JSONObject responseData) {
        Set<String> uniqueFieldNames = new LinkedHashSet<>();
        List<FormField> fields = getFormFields();
        List<Object> params = new ArrayList<>();
        params.add(phoneNumber);

        StringBuilder query = new StringBuilder("INSERT INTO agm_responses (phone_number");
        StringBuilder values = new StringBuilder("VALUES (?");

        for (FormField field : fields) {
            String fieldName = field.getName().replace(" ", "_").toLowerCase();
            if (uniqueFieldNames.add(fieldName)) {
                query.append(", ").append(fieldName);
                values.append(", ?");
                params.add(responseData.optString(field.getName()));
            }
        }

        query.append(") ").append(values).append(")");

        javax.persistence.Query nativeQuery = entityManager.createNativeQuery(query.toString());
        for (int i = 0; i < params.size(); i++) {
            nativeQuery.setParameter(i + 1, params.get(i));
        }
        nativeQuery.executeUpdate();
    }

    public boolean hasResponse(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM agm_responses WHERE phone_number = :phoneNumber";
        Object result = entityManager.createNativeQuery(query)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
        long count = (result instanceof Number) ? ((Number) result).longValue() : 0L;
        return count > 0;
    }

    public boolean isLifeMember(String memberId) {
        return agmMembershipService.isLifeMember(memberId);
    }

    private String getSQLType(String fieldType) {
        switch (fieldType) {
            case "text": case "email": case "url": return "VARCHAR(255)";
            case "textarea": return "TEXT";
            case "number": return "INT";
            case "select": case "checkbox": return "VARCHAR(50)";
            case "date": return "DATE";
            default: return "VARCHAR(255)";
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Add the missing methods
	@Transactional(readOnly = true)
    public List<Object[]> getAgmResponses(List<FormField> fields) {
        StringBuilder query = new StringBuilder("SELECT phone_number");
        for (FormField field : fields) {
            String fieldName = field.getName().replace(" ", "_").toLowerCase();
            query.append(", ").append(fieldName);
        }
        query.append(" FROM agm_responses");
        return entityManager.createNativeQuery(query.toString()).getResultList();
    }

    @Transactional(readOnly = true)
    public boolean isAgmFormActive() {
        AlumniSettings settings = entityManager.find(AlumniSettings.class, 1L);
        return settings != null && settings.isAgmFormActive();
    }

    @Transactional
    public void setAgmFormActive(boolean active) {
        AlumniSettings settings = entityManager.find(AlumniSettings.class, 1L);
        if (settings == null) {
            settings = new AlumniSettings();
            settings.setId(1L);
        }
        settings.setAgmFormActive(active);
        entityManager.merge(settings);
    }
}