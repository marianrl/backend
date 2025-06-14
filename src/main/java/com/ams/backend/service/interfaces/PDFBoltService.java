package com.ams.backend.service.interfaces;

import com.ams.backend.request.PdfGenerationRequest;
import com.ams.backend.response.PDFBoltResponse;

public interface PDFBoltService {
  PDFBoltResponse generatePDF(PdfGenerationRequest request);
}