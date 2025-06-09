package com.shc.alumni.springboot.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.shc.alumni.springboot.controller.PdfFontLoader;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BillingService {
    private static final Logger logger = LoggerFactory.getLogger(BillingService.class);

    public byte[] generateBillPdf(String memberId, String fullName, String phoneNumber, String paymentId) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();

            Document document = new Document(pdfDocument, PageSize.A4);

            // Load fonts with fallback to standard font
            PdfFont font;
            PdfFont boldFont;
            try {
                font = PdfFontLoader.loadFont("fonts/RobotoSlab.ttf");
                boldFont = PdfFontLoader.loadFont("fonts/RobotoSerif.ttf");
            } catch (Exception e) {
                logger.warn("Custom fonts not loaded, falling back to Helvetica: {}", e.getMessage());
                font = PdfFontFactory.createFont("Helvetica");
                boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            }

            // Add watermark
            addWatermark(pdfDocument, "PAID");

            // Load logo image
            byte[] logoBytes = loadResourceAsByteArray("static/logo.png");
            if (logoBytes == null) {
                logger.warn("Logo file not found: static/logo.png, skipping logo");
            } else {
                ImageData logoData = ImageDataFactory.create(logoBytes);
                Image logo = new Image(logoData).scaleToFit(100, 100);
                logo.setFixedPosition(50, pdfDocument.getFirstPage().getPageSize().getTop() - 120);
                document.add(logo);
            }

            document.add(new Paragraph("SACRED HEART COLLEGE (Autonomous), Tirupattur - 635 601, Tirupattur Dt. \n Accredited by NAAC (5th cycle - under RAF) with a CGPA of 3.53/4 at 'A++' Grade, Affiliated to Thiruvalluvar University")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
            );

            document.add(new Paragraph("Membership Bill")
                    .setFont(boldFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
            );

            document.add(new Paragraph("\n\n\nMember ID: " + memberId).setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Full Name: " + fullName).setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Phone Number: " + phoneNumber).setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Payment ID: " + paymentId).setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\nThank you for your payment!").setFont(boldFont).setFontSize(14).setTextAlignment(TextAlignment.CENTER));

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            logger.error("Error generating bill PDF", e);
            return null;
        }
    }

    private void addWatermark(PdfDocument pdfDocument, String watermarkText) throws Exception {
        for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
            PdfCanvas canvas = new PdfCanvas(pdfDocument.getPage(i));
            canvas.saveState();
            canvas.setFillColor(ColorConstants.LIGHT_GRAY);
            PdfFont watermarkFont = PdfFontFactory.createFont("Helvetica"); // Use standard font for watermark
            canvas.setFontAndSize(watermarkFont, 65);

            Rectangle pageSize = pdfDocument.getPage(i).getPageSize();
            float x = pageSize.getWidth() / 2;
            float y = pageSize.getHeight() / 2;

            canvas.beginText()
                  .setTextMatrix(70, 70)
                  .moveText(x - 100, y)
                  .showText(watermarkText)
                  .endText();

            canvas.restoreState();
        }
    }

    private byte[] loadResourceAsByteArray(String resourcePath) {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                return null;
            }
            return resourceStream.readAllBytes();
        } catch (Exception e) {
            logger.error("Error loading resource: " + resourcePath, e);
            return null;
        }
    }
}