package com.ams.backend.controller;

import com.ams.backend.service.CloudmersiveService;
import com.cloudmersive.client.invoker.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cloudmersive")
public class CloudmersiveController {

  private final CloudmersiveService cloudmersiveService;

  public CloudmersiveController(CloudmersiveService cloudmersiveService) {
    this.cloudmersiveService = cloudmersiveService;
  }

  @PostMapping("/convert-excel-to-json")
  public ResponseEntity<?> convertExcelToJson(@RequestParam("file") MultipartFile file) {
    try {
      List<Map<String, String>> jsonResult = cloudmersiveService.convertExcelToJson(file);
      return ResponseEntity.ok(jsonResult);
    } catch (IOException | ApiException e) {
      return ResponseEntity.internalServerError().body("Error processing file: " + e.getMessage());
    }
  }
}
