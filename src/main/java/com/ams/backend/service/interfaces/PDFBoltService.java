package com.ams.backend.service.interfaces;

import com.ams.backend.response.PDFBoltResponse;

public interface PDFBoltService {
  PDFBoltResponse generatePDF(String url);
}