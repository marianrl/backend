package com.ams.backend.service;

import com.ams.backend.client.PDFBoltClient;
import com.ams.backend.response.PDFBoltResponse;
import com.ams.backend.service.interfaces.PDFBoltService;
import org.springframework.stereotype.Service;

@Service
public class PDFBoltServiceImpl implements PDFBoltService {
  private final PDFBoltClient pdfBoltClient;

  public PDFBoltServiceImpl(PDFBoltClient pdfBoltClient) {
    this.pdfBoltClient = pdfBoltClient;
  }

  @Override
  public PDFBoltResponse generatePDF(String url) {
    return pdfBoltClient.generatePDF(url);
  }
}