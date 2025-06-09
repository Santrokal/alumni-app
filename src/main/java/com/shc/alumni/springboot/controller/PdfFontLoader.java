package com.shc.alumni.springboot.controller;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.PdfEncodings;
import org.apache.commons.io.IOUtils; // Import Apache Commons IO

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class PdfFontLoader {
    public static PdfFont loadFont(String fontPath) throws IOException {
        try (InputStream fontStream = PdfFontLoader.class.getClassLoader().getResourceAsStream(fontPath)) {
            if (fontStream == null) {
                throw new RuntimeException("Font file not found in resources: " + fontPath);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(fontStream, outputStream); // ✅ Fixed: Use Apache Commons IO

            return PdfFontFactory.createFont(outputStream.toByteArray(), PdfEncodings.UTF8, 
                    PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED); // ✅ Fixed: Correct method signature
        }
    }
}




