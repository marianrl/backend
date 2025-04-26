package com.ams.backend.controller;

import com.ams.backend.service.CloudmersiveService;
import com.cloudmersive.client.invoker.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudmersiveControllerTest {

  @Mock
  private CloudmersiveService cloudmersiveService;

  private CloudmersiveController cloudmersiveController;

  @BeforeEach
  void setUp() {
    cloudmersiveController = new CloudmersiveController(cloudmersiveService);
  }

  @Test
  void convertExcelToJson_Success() throws IOException, ApiException {
    // Arrange
    MultipartFile file = new MockMultipartFile("test.xlsx", "test.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test".getBytes());
    List<Map<String, String>> expectedResult = List.of(Map.of("key", "value"));
    when(cloudmersiveService.convertExcelToJson(any(MultipartFile.class))).thenReturn(expectedResult);

    // Act
    ResponseEntity<?> response = cloudmersiveController.convertExcelToJson(file);

    // Assert
    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(expectedResult, response.getBody());
    verify(cloudmersiveService).convertExcelToJson(file);
  }

  @Test
  void convertExcelToJson_IOException() throws IOException, ApiException {
    // Arrange
    MultipartFile file = new MockMultipartFile("test.xlsx", "test.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test".getBytes());
    when(cloudmersiveService.convertExcelToJson(any(MultipartFile.class)))
        .thenThrow(new IOException("Test IO Exception"));

    // Act
    ResponseEntity<?> response = cloudmersiveController.convertExcelToJson(file);

    // Assert
    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    assertNotNull(response.getBody());
    String errorMessage = (String) response.getBody();
    assertNotNull(errorMessage);
    assertTrue(errorMessage.contains("Error processing file"));
    verify(cloudmersiveService).convertExcelToJson(file);
  }

  @Test
  void convertExcelToJson_ApiException() throws IOException, ApiException {
    // Arrange
    MultipartFile file = new MockMultipartFile("test.xlsx", "test.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test".getBytes());
    when(cloudmersiveService.convertExcelToJson(any(MultipartFile.class)))
        .thenThrow(new ApiException("Test API Exception"));

    // Act
    ResponseEntity<?> response = cloudmersiveController.convertExcelToJson(file);

    // Assert
    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    assertNotNull(response.getBody());
    String errorMessage = (String) response.getBody();
    assertNotNull(errorMessage);
    assertTrue(errorMessage.contains("Error processing file"));
    verify(cloudmersiveService).convertExcelToJson(file);
  }
}