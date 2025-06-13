package com.ams.backend.service;

import com.ams.backend.client.PDFBoltClient;
import com.ams.backend.dto.PdfGenerationRequest;
import com.ams.backend.response.PDFBoltResponse;
import com.ams.backend.service.interfaces.PDFBoltService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PDFBoltServiceImpl implements PDFBoltService {
  private final PDFBoltClient pdfBoltClient;
  private final TemplateEngine templateEngine;

  public PDFBoltServiceImpl(
      PDFBoltClient pdfBoltClient,
      TemplateEngine templateEngine) {
    this.pdfBoltClient = pdfBoltClient;
    this.templateEngine = templateEngine;
  }

  @Override
  public PDFBoltResponse generatePDF(PdfGenerationRequest request) {
    try {
      // Create context for template
      Context context = new Context();
      context.setVariable("pageData", request.getPageData());
      context.setVariable("generatedDate",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

      // Render template to HTML
      String htmlContent = templateEngine.process("reports-template", context);

      // Generate PDF using PDFBolt with HTML content
      return pdfBoltClient.generatePDF(htmlContent);
    } catch (Exception e) {
      throw new RuntimeException("Error generating PDF", e);
    }
  }
}