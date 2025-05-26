package com.ams.backend.response;

import lombok.Data;

@Data
public class PDFBoltResponse {
  private String requestId;
  private String status;
  private String errorCode;
  private String errorMessage;
  private String documentUrl;
  private String expiresAt;
}