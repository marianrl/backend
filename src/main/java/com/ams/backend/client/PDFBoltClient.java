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

  public PDFBoltResponse generatePDF(String url) {
    String endpoint = baseUrl + "/sync";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("API_KEY", apiKey);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("url", url);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

    return restTemplate.postForObject(endpoint, request, PDFBoltResponse.class);
  }
}