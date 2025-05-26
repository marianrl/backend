package com.ams.backend.controller;

import com.ams.backend.response.PDFBoltResponse;
import com.ams.backend.service.interfaces.PDFBoltService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pdf")
@CrossOrigin(origins = "*")
public class PDFBoltController {
  private final PDFBoltService pdfBoltService;

  public PDFBoltController(PDFBoltService pdfBoltService) {
    this.pdfBoltService = pdfBoltService;
  }

  @PostMapping("/generate")
  public ResponseEntity<PDFBoltResponse> generatePDF(@RequestBody Map<String, String> request) {
    String url = request.get("url");
    PDFBoltResponse response = pdfBoltService.generatePDF(url);
    return ResponseEntity.ok(response);
  }
}