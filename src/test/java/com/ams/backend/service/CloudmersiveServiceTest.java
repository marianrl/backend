package com.ams.backend.service;

import com.ams.backend.client.CloudmersiveClient;
import com.cloudmersive.client.invoker.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudmersiveServiceTest {

  @Mock
  private CloudmersiveClient cloudmersiveClient;

  @Mock
  private MultipartFile multipartFile;

  private CloudmersiveService cloudmersiveService;

  @BeforeEach
  void setUp() {
    cloudmersiveService = new CloudmersiveService(cloudmersiveClient);
  }

  @Test
  void convertExcelToJson_Success() throws IOException, ApiException {
    // Arrange
    List<Map<String, String>> expectedResult = List.of(Map.of("key", "value"));
    when(cloudmersiveClient.convertExcelToJson(any(File.class))).thenReturn(expectedResult);

    // Act
    List<Map<String, String>> result = cloudmersiveService.convertExcelToJson(multipartFile);

    // Assert
    assertNotNull(result);
    assertEquals(expectedResult, result);
    verify(cloudmersiveClient).convertExcelToJson(any(File.class));
  }

  @Test
  void convertExcelToJson_IOException() throws IOException {
    // Arrange
    doThrow(new IOException("Test IO Exception")).when(multipartFile).transferTo(any(File.class));

    // Act & Assert
    assertThrows(IOException.class, () -> cloudmersiveService.convertExcelToJson(multipartFile));
  }

  @Test
  void convertExcelToJson_ApiException() throws IOException, ApiException {
    // Arrange
    when(cloudmersiveClient.convertExcelToJson(any(File.class)))
        .thenThrow(new ApiException("Test API Exception"));

    // Act & Assert
    assertThrows(ApiException.class, () -> cloudmersiveService.convertExcelToJson(multipartFile));
  }
}