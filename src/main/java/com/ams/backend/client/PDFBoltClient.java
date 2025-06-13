package com.ams.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ams.backend.response.PDFBoltResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@Component
public class PDFBoltClient {
  private final RestTemplate restTemplate;
  private final String apiKey;
  private final String baseUrl = "https://api.pdfbolt.com/v1";

  public PDFBoltClient(
      RestTemplate restTemplate,
      @Value("${pdfbolt.api.key}") String apiKey) {
    this.restTemplate = restTemplate;
    this.apiKey = apiKey;
  }

  public PDFBoltResponse generatePDF(String htmlContent) {
    String endpoint = baseUrl + "/sync";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("API_KEY", apiKey);

    // Encode HTML content to Base64
    String base64Html = Base64.getEncoder().encodeToString(htmlContent.getBytes());

    // Create the request body with just the HTML content
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("html", base64Html);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

    try {
      return restTemplate.postForObject(endpoint, request, PDFBoltResponse.class);
    } catch (Exception e) {
      PDFBoltResponse errorResponse = new PDFBoltResponse();
      errorResponse.setStatus("ERROR");
      errorResponse.setErrorMessage("Error generating PDF: " + e.getMessage());
      return errorResponse;
    }
  }
}