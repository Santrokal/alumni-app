package com.shc.alumni.springboot.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.service.AlumniRegisterService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ExportController {

    @Autowired
    private AlumniRegisterService alumniRegisterService;

    @GetMapping("/exportEntireTable")
    public ResponseEntity<byte[]> exportEntireTableToExcel() throws IOException {
        List<AlumniRegisterEntity> alumniList = alumniRegisterService.getAllAlumni();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Alumni Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "ID", "Full Name", "Father's Name", "Nationality", "DOB", "Gender",
                "House Flat Number", "Street Name","City" , "State" , "Postal Code",
                "Landmark" , "Area",
                "Shift", "Department", "Degree Obtained", "Stay From",
                "Stay To", "Marital Status", "Anniversary Year", "WhatsApp No", 
                "Phone No", "Email Address", "Employment Status", "Job Designation",
                "Office Phone No", "Office Email", "Field of Expertise", "Photograph"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Populate data rows
            int rowIdx = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (AlumniRegisterEntity alumni : alumniList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(alumni.getId());
                row.createCell(1).setCellValue(safeString(alumni.getFullname()));
                row.createCell(2).setCellValue(safeString(alumni.getFathersname()));
                row.createCell(3).setCellValue(safeString(alumni.getNationality()));
                row.createCell(4).setCellValue(alumni.getDob() != null ? alumni.getDob().format(formatter) : "");
                row.createCell(5).setCellValue(safeString(alumni.getGender()));
                row.createCell(6).setCellValue(safeString(alumni.getHouse_flat_number()));
                row.createCell(7).setCellValue(safeString(alumni.getStreet_name()));
                row.createCell(8).setCellValue(safeString(alumni.getCity()));
                row.createCell(9).setCellValue(safeString(alumni.getState()));
                row.createCell(10).setCellValue(safeString(alumni.getPostal_code()));
                row.createCell(11).setCellValue(safeString(alumni.getLandmark()));
                row.createCell(12).setCellValue(safeString(alumni.getArea()));
                row.createCell(13).setCellValue(safeString(alumni.getShift()));
                row.createCell(14).setCellValue(safeString(alumni.getDepartment()));
                row.createCell(15).setCellValue(safeString(alumni.getDegree_obtained()));
                row.createCell(16).setCellValue(alumni.getShcStayFrom() != null ? alumni.getShcStayFrom() : "");
                row.createCell(17).setCellValue(alumni.getShcStayTo() != null ? alumni.getShcStayTo() : "");
                row.createCell(18).setCellValue(safeString(alumni.getMarital_status()));
                row.createCell(19).setCellValue(alumni.getAnniversary_year() != null ? alumni.getAnniversary_year().format(formatter) : "");
                row.createCell(20).setCellValue(safeString(alumni.getWhatsappno()));
                row.createCell(21).setCellValue(safeString(alumni.getPhoneno()));
                row.createCell(22).setCellValue(safeString(alumni.getEmailaddress()));
                row.createCell(23).setCellValue(safeString(alumni.getEmpstatus()));
                row.createCell(24).setCellValue(safeString(alumni.getJobdesig()));
                row.createCell(25).setCellValue(safeString(alumni.getOfficephoneno()));
                row.createCell(26).setCellValue(safeString(alumni.getOfficeemail()));
                row.createCell(27).setCellValue(safeString(alumni.getFieldofexpert()));
                row.createCell(28).setCellValue(alumni.getFilePath() != null ? "Available" : "Not Available");
            }

            // Adjust column widths for readability
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write data to the output stream
            workbook.write(out);
            byte[] excelData = out.toByteArray();

            // Prepare HTTP response with correct headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Disposition", "attachment; filename=EntireTableData.xlsx");
            responseHeaders.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(excelData);
        }
    }


    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private String safeString(String value) {
        return value != null ? value : "";
    }
}
