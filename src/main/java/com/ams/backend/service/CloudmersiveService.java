package com.ams.backend.service;

import com.ams.backend.client.CloudmersiveClient;
import com.cloudmersive.client.invoker.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CloudmersiveService {

  private final CloudmersiveClient cloudmersiveClient;

  public CloudmersiveService(CloudmersiveClient cloudmersiveClient) {
    this.cloudmersiveClient = cloudmersiveClient;
  }

  public List<Map<String, String>> convertExcelToJson(MultipartFile file) throws IOException, ApiException {
    // Guardar el archivo temporalmente
    File tempFile = File.createTempFile("upload-", ".xlsx");
    file.transferTo(tempFile);

    try {
      return cloudmersiveClient.convertExcelToJson(tempFile);
    } finally {
      // Asegurar que el archivo temporal se elimine después de su uso
      tempFile.delete();
    }
  }
}
