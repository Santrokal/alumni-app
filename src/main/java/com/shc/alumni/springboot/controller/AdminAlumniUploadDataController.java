package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.entity.BillPdfEntity;
import com.shc.alumni.springboot.entity.MembershipEntity;
import com.shc.alumni.springboot.repository.BillPdfRepository;
import com.shc.alumni.springboot.repository.MembershipRepository;
import com.shc.alumni.springboot.service.AlumniRegisterService;
import com.shc.alumni.springboot.service.BillingService;

import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.String;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class AdminAlumniUploadDataController {

    @Autowired
    private AlumniRegisterService alumniRegisterService;
    
    
    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private BillPdfRepository billPdfRepository;

    @Autowired
    private BillingService billingService;

    private static final List<String> REQUIRED_HEADERS = Arrays.asList("fullname", "phone_number");

    
    
    @PostMapping("/admin/uploadMembershipData")
    @Transactional
    public String uploadMembershipData(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/admin/uploaddata";
        }

        List<String> duplicatePhones = new ArrayList<>();
        List<String> successUploads = new ArrayList<>();
        Set<String> phoneNumbersInFile = new HashSet<>();
        String uploadDir = "C:/uploads/bills/"; // Adjust path for your environment

        try {
            // Ensure upload directory exists
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                redirectAttributes.addFlashAttribute("message", "The file is empty or has no header row.");
                workbook.close();
                return "redirect:/admin/uploaddata";
            }

            // Validate Header Row
            Row headerRow = rowIterator.next();
            Map<String, Integer> columnMapping = validateAndMapHeaders(headerRow);

            if (columnMapping.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Invalid file format. Required headers: " + REQUIRED_HEADERS);
                workbook.close();
                return "redirect:/admin/uploaddata";
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String fullName = getCellValue(row, columnMapping.get("fullname"));
                String phoneNumber = getCellValue(row, columnMapping.get("phone_number"));

                if (fullName.isEmpty() || phoneNumber.isEmpty()) {
                    continue;
                }

                if (!phoneNumbersInFile.add(phoneNumber) || membershipRepository.existsByPhoneNumber(phoneNumber)) {
                    duplicatePhones.add(phoneNumber);
                    continue;
                }

                String memberId = generateMemberId(fullName);
                String paymentId = UUID.randomUUID().toString().substring(0, 10);

                // Save Membership Data
                MembershipEntity membership = new MembershipEntity();
                membership.setMember_id(memberId);
                membership.setFullname(fullName);
                membership.setPhoneNumber(phoneNumber);
                membership.setPaymentId(paymentId);
                membership.setCreatedAt(LocalDateTime.now());
                membershipRepository.save(membership);
                successUploads.add(phoneNumber);

                // Generate and Save PDF
                try {
                    byte[] pdfData = billingService.generateBillPdf(memberId, fullName, phoneNumber, paymentId);
                    String pdfFilePath = uploadDir + memberId + "_" + paymentId + ".pdf";
                    try (FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
                        fos.write(pdfData);
                    }

                    BillPdfEntity billPdf = new BillPdfEntity();
                    billPdf.setId(UUID.randomUUID().toString());
                    billPdf.setMemberId(memberId);
                    billPdf.setFullName(fullName);
                    billPdf.setPhoneNumber(phoneNumber);
                    billPdf.setPaymentId(paymentId);
                    billPdf.setStatus("PAID");
                    billPdf.setDate(new Date());
                    billPdf.setPdfData(pdfData);
                    billPdfRepository.save(billPdf);
                } catch (Exception e) {
                    // Log error, e.g., logger.error("Failed to generate/save PDF for member: " + memberId, e);
                    continue;
                }
            }

            workbook.close();

            // Set feedback message
            StringBuilder message = new StringBuilder();
            if (!duplicatePhones.isEmpty()) {
                message.append("Skipped ").append(duplicatePhones.size()).append(" duplicate phone numbers: ").append(duplicatePhones);
            }
            if (!successUploads.isEmpty()) {
                if (message.length() > 0) message.append("; ");
                message.append("Successfully uploaded ").append(successUploads.size()).append(" records.");
            }
            if (message.length() == 0) {
                message.append("No valid records found.");
            }
            redirectAttributes.addFlashAttribute("message", message.toString());

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Error processing file: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Unexpected error: " + e.getMessage());
        }

        return "redirect:/admin/uploaddata";
    }

    private Map<String, Integer> validateAndMapHeaders(Row headerRow) {
        Map<String, Integer> columnMapping = new HashMap<>();
        for (Cell cell : headerRow) {
            if (cell == null || cell.getCellType() == CellType.BLANK) continue;
            String header = cell.getStringCellValue().trim().toLowerCase();
            if (REQUIRED_HEADERS.contains(header)) {
                columnMapping.put(header, cell.getColumnIndex());
            }
        }
        return columnMapping.size() == REQUIRED_HEADERS.size() ? columnMapping : Collections.emptyMap();
    }

    private String getCellValue(Row row, Integer columnIndex) {
        if (columnIndex == null || row.getCell(columnIndex) == null) {
            return "";
        }

        Cell cell = row.getCell(columnIndex);
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        // Format numeric values (e.g., phone numbers) without scientific notation
                        return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                    }
                case BOOLEAN:
                    return Boolean.toString(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.getCellFormula();
                case BLANK:
                default:
                    return "";
            }
        } catch (Exception e) {
            return ""; // Handle invalid cell data gracefully
        }
    }

    private String generateMemberId(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "MEM-" + UUID.randomUUID().toString().substring(0, 5);
        }
        String namePart = fullName.replaceAll("\\s+", "").substring(0, Math.min(5, fullName.length())).toUpperCase();
        return namePart + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Display the file upload form
    @GetMapping("/admin/uploaddata")
    public String showAddUserForm(HttpSession session, Model model) {
        // Retrieve the logged-in admin from session
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
        if (loggedInAdmin == null) {
            return "redirect:/";
        }

        // Encode admin image from file path
        String base64Image = "";
        if (loggedInAdmin.getImagePath() != null) {
            try {
                byte[] imageBytes = Files.readAllBytes(new File(loggedInAdmin.getImagePath()).toPath());
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		

        // Add attributes to model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        return "uploaddata"; // Ensure `uploaddata.jsp` exists
    }


    // Handle file upload
    @PostMapping("/admin/uploadAlumniData")
    public String uploadAlumniData(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/admin/uploaddata"; // Redirect back to the form
        }

        try {
            String filename = file.getOriginalFilename();
            if (filename != null && (filename.endsWith(".xlsx") || filename.endsWith(".csv"))) {
                List<AlumniRegisterEntity> alumniList = null;

                if (filename.endsWith(".xlsx")) {
                    alumniList = parseXLSXFile(file.getInputStream());
                } else if (filename.endsWith(".csv")) {
                    alumniList = parseCSVFile(file.getInputStream());
                }

                if (alumniList != null && !alumniList.isEmpty()) {
                    alumniRegisterService.saveAll(alumniList);
                    redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("message", "No valid data found in the file.");
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Please upload a valid CSV or XLSX file.");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Error reading the file.");
        }

        return "redirect:/admin/uploaddata"; // Redirect back to the form with the message
    }

    // Parse XLSX file
    private List<AlumniRegisterEntity> parseXLSXFile(InputStream inputStream) throws IOException {
        List<AlumniRegisterEntity> alumniList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        
        // Read the header row
        Row headerRow = sheet.getRow(0);
        Map<Integer, String> columnMapping = new HashMap<>();
        
        for (Cell cell : headerRow) {
            columnMapping.put(cell.getColumnIndex(), cell.getStringCellValue().trim().toLowerCase());
        }

        // Process data rows
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip the header row
            
            AlumniRegisterEntity alumni = new AlumniRegisterEntity();
            
            for (Cell cell : row) {
                String columnName = columnMapping.get(cell.getColumnIndex());
                String cellValue = getCellValue(cell);
                
                if (columnName == null) continue; // Skip unmapped columns
                
                // Map Excel columns to entity fields
                switch (columnName) {
                    case "fullname":
                        alumni.setFullname(cellValue);
                        break;
                    case "fathersname":
                        alumni.setFathersname(cellValue);
                        break;
                    case "nationality":
                        alumni.setNationality(cellValue);
                        break;
                    case "dob":
                        alumni.setDob(getDateCellValue(cell));
                        break;
                    case "gender":
                        alumni.setGender(cellValue);
                        break;
                    case "house_flat_number":
                        alumni.setHouse_flat_number(cellValue);
                        break;
                    case "street_name":
                        alumni.setStreet_name(cellValue);
                        break;
                    case "city":
                        alumni.setCity(cellValue);
                        break;
                    case "state":
                        alumni.setState(cellValue);
                        break;
                    case "postal_code":
                        alumni.setPostal_code(cellValue);
                        break;
                    case "landmark":
                        alumni.setLandmark(cellValue);
                        break;
                    case "area":
                        alumni.setArea(cellValue);
                        break;
                    case "address_type":
                        alumni.setAddress_type(cellValue);
                        break;
                    case "shift":
                        alumni.setShift(cellValue);
                        break;
                    case "department":
                        alumni.setDepartment(cellValue);
                        break;
                    case "degree_obtained":
                        alumni.setDegree_obtained(cellValue);
                        break;
                    case "shcstayfrom":
                        alumni.setShcStayFrom(cellValue);
                        break;
                    case "shcstayto":
                        alumni.setShcStayTo(cellValue);
                        break;
                    case "marital_status":
                        alumni.setMarital_status(cellValue);
                        break;
                    case "anniversary_year":
                        alumni.setAnniversary_year(getDateCellValue(cell));
                        break;
                    case "whatsappno":
                        alumni.setWhatsappno(cellValue);
                        break;
                    case "phoneno":
                        alumni.setPhoneno(cellValue);
                        break;
                    case "emailaddress":
                        alumni.setEmailaddress(cellValue);
                        break;
                    case "empstatus":
                        alumni.setEmpstatus(cellValue);
                        break;
                    case "jobdesig":
                        alumni.setJobdesig(cellValue);
                        break;
                    case "officephoneno":
                        alumni.setOfficephoneno(cellValue);
                        break;
                    case "officeemail":
                        alumni.setOfficeemail(cellValue);
                        break;
                    case "fieldofexpert":
                        alumni.setFieldofexpert(cellValue);
                        break;
                }
            }
            alumniList.add(alumni);
        }
        workbook.close();
        return alumniList;
    }





    private LocalDate getDateCellValue(Cell cell) {
        if (cell == null) return null; // Return null if the cell is empty
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null; // Return null if the cell is not a valid date
    }


    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(cell.getDateCellValue());
                } else {
                    // Check if it's a whole number
                    if (cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue())) {
                        return String.valueOf((long) cell.getNumericCellValue()); // Convert to long to remove .0
                    } else {
                        return String.valueOf(cell.getNumericCellValue()); // Keep as double if not whole
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }



	// Parse CSV file
    private List<AlumniRegisterEntity> parseCSVFile(InputStream inputStream) throws IOException {
        List<AlumniRegisterEntity> alumniList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                AlumniRegisterEntity alumni = new AlumniRegisterEntity();

                alumni.setFullname(columns[0]);
                alumni.setFathersname(columns[1]);
                alumni.setNationality(columns[2]);
                alumni.setDob(parseDate(columns[3]));
                alumni.setGender(columns[4]);
                alumni.setHouse_flat_number(columns[5]);
                alumni.setStreet_name(columns[6]);
                alumni.setCity(columns[7]);
                alumni.setState(columns[8]);
                alumni.setPostal_code(columns[9]);
                alumni.setLandmark(columns[10]);
                alumni.setArea(columns[11]);
                alumni.setAddress_type(columns[12]);
                alumni.setShift(columns[13]);
                alumni.setDepartment(columns[14]);
                alumni.setDegree_obtained(columns[15]);
                alumni.setShcStayFrom(columns[16]);
                alumni.setShcStayTo(columns[17]);
                alumni.setMarital_status(columns[18]);
                alumni.setAnniversary_year(parseDate(columns[19]));
                alumni.setWhatsappno(columns[20]);
                alumni.setPhoneno(columns[21]);
                alumni.setEmailaddress(columns[22]);
                alumni.setEmpstatus(columns[23]);
                alumni.setJobdesig(columns[24]);
                alumni.setOfficephoneno(columns[25]);
                alumni.setOfficeemail(columns[26]);
                alumni.setFieldofexpert(columns[27]);
                alumni.setFilePath(null); // Set this to null, or handle file uploads separately

                alumniList.add(alumni);
            }
        }
        return alumniList;
    }


    // Helper method to parse date from CSV string
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
    
}
