package com.ams.backend.controller;

import com.ams.backend.dto.PdfGenerationRequest;
import com.ams.backend.response.PDFBoltResponse;
import com.ams.backend.service.interfaces.PDFBoltService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pdf")
@CrossOrigin(origins = "*")
public class PDFBoltController {
  private final PDFBoltService pdfBoltService;

  public PDFBoltController(PDFBoltService pdfBoltService) {
    this.pdfBoltService = pdfBoltService;
  }

  @PostMapping("/generate")
  public ResponseEntity<PDFBoltResponse> generatePDF(@RequestBody PdfGenerationRequest request) {
    PDFBoltResponse response = pdfBoltService.generatePDF(request);
    return ResponseEntity.ok(response);
  }
}