package com.shc.alumni.springboot.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shc.alumni.springboot.entity.FormField;
import com.shc.alumni.springboot.service.FormService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.List;

@Controller
public class ExportTableController {

    private static final Logger logger = LoggerFactory.getLogger(ExportTableController.class);

    @Autowired
    private FormService formService; // Assuming this is your service class

    @PostMapping("/admin/agmexporttable")
    public void exportTable(@RequestParam("format") String format, HttpSession session, HttpServletResponse response) throws IOException {
        if (!"admin".equals(session.getAttribute("role"))) {
            logger.warn("Unauthorized attempt to export table. Redirecting to login.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access.");
            return;
        }

        String currentYear = String.valueOf(Year.now().getValue());
        String filename = "AGM_Responses_" + currentYear + "." + format;

        // Fetch from session first
        List<FormField> fields = (List<FormField>) session.getAttribute("fields");
        List<Object[]> responses = (List<Object[]>) session.getAttribute("responses");

        // If not in session, fetch from service
        if (fields == null || responses == null) {
            try {
                fields = formService.getFormFields(); // Assume this method exists in FormService
                responses = formService.getAgmResponses(fields); // Assume this method exists in FormService
                // Store in session for future use
                session.setAttribute("fields", fields);
                session.setAttribute("responses", responses);
            } catch (Exception e) {
                logger.error("Error fetching table data from service", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching table data.");
                return;
            }
        }

        if (fields == null || fields.isEmpty() || responses == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No table data available for export.");
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try {
            switch (format.toLowerCase()) {
                case "xlsx":
                    exportToExcel(fields, responses, response);
                    break;
                case "pdf":
                    exportToPDF(fields, responses, response);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported format: " + format);
                    return;
            }
            session.setAttribute("isTableExported", true);
            logger.info("Table exported successfully in {} format.", format);
        } catch (IOException e) {
            logger.error("Error exporting table", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating export file.");
        }
    }

    @PostMapping("/admin/deactivate-agm-form")
    public String deactivateAgmForm(HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            logger.warn("Unauthorized attempt to deactivate AGM form. Redirecting to login.");
            return "redirect:/";
        }

        Boolean isTableExported = (Boolean) session.getAttribute("isTableExported");
        if (isTableExported == null || !isTableExported) {
            logger.info("AGM form deactivation attempted without exporting table.");
            return "redirect:/admin/agm-responses?error=Please export the table before deactivating";
        }

        try {
            formService.setAgmFormActive(false);
            session.removeAttribute("isTableExported");
            logger.info("AGM form deactivated by admin.");
            return "redirect:/admin/adminhome?message=AGM Form deactivated successfully";
        } catch (Exception e) {
            logger.error("Error deactivating AGM form", e);
            return "redirect:/admin/agm-responses?error=An error occurred while deactivating the AGM form: " + e.getMessage();
        }
    }

    @PostMapping("/admin/activate-agm-form")
    public String activateAgmForm(HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            logger.warn("Unauthorized attempt to activate AGM form. Redirecting to login.");
            return "redirect:/";
        }

        try {
            formService.setAgmFormActive(true);
            logger.info("AGM form activated by admin.");
            return "redirect:/admin/adminhome?message=AGM Form activated successfully";
        } catch (Exception e) {
            logger.error("Error activating AGM form", e);
            return "redirect:/admin/agm-responses?error=Please export the table before deactivating";
            }
    }

    private void exportToExcel(List<FormField> fields, List<Object[]> responses, HttpServletResponse response) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("AGM Responses");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Phone Number");
            for (int i = 0; i < fields.size(); i++) {
                headerRow.createCell(i + 1).setCellValue(fields.get(i).getName());
            }
            for (int i = 0; i < responses.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Object[] resp = responses.get(i);
                for (int j = 0; j < resp.length; j++) {
                    row.createCell(j).setCellValue(resp[j] != null ? resp[j].toString() : "");
                }
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            workbook.write(response.getOutputStream());
        }
    }

    private void exportToPDF(List<FormField> fields, List<Object[]> responses, HttpServletResponse response) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            PdfPTable table = new PdfPTable(fields.size() + 1);
            table.setWidthPercentage(100);
            table.addCell("Phone Number");
            for (FormField field : fields) {
                table.addCell(field.getName());
            }
            for (Object[] resp : responses) {
                for (Object column : resp) {
                    table.addCell(column != null ? column.toString() : "");
                }
            }
            document.add(table);
            document.close();
            response.setContentType("application/pdf");
            response.getOutputStream().write(baos.toByteArray());
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }

    private void exportToDocx(List<FormField> fields, List<Object[]> responses, HttpServletResponse response) throws IOException {
        WordprocessingMLPackage wordMLPackage = null;
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
            Tbl table = new Tbl();
            Tr headerRow = new Tr();
            headerRow.getContent().add(createTableCell("Phone Number"));
            for (FormField field : fields) {
                headerRow.getContent().add(createTableCell(field.getName()));
            }
            table.getContent().add(headerRow);
            for (Object[] resp : responses) {
                Tr row = new Tr();
                for (Object column : resp) {
                    row.getContent().add(createTableCell(column != null ? column.toString() : ""));
                }
                table.getContent().add(row);
            }
            mainDocumentPart.addObject(table);
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            wordMLPackage.save(response.getOutputStream());
        } catch (Exception e) {
            throw new IOException("Error generating DOCX", e);
        } finally {
            if (wordMLPackage != null) {
                // No explicit close method; resources managed by save()
            }
        }
    }

    private Tc createTableCell(String content) {
        Tc tc = new Tc();
        P p = new P();
        R r = new R();
        Text text = new Text();
        text.setValue(content);
        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        return tc;
    }
}