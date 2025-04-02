package com.ams.backend.client;

import com.cloudmersive.client.ConvertDataApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CloudmersiveClient {

    private final ConvertDataApi apiInstance;
    private final ObjectMapper objectMapper;

    public CloudmersiveClient(@Value("${cloudmersive.api.key}") String apiKey) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configurar la API Key
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
        apiKeyAuth.setApiKey(apiKey);

        this.apiInstance = new ConvertDataApi(defaultClient);
        this.objectMapper = new ObjectMapper();
    }

    public List<Map<String, String>> convertExcelToJson(File inputFile) throws IOException, ApiException {
        Object response = apiInstance.convertDataXlsxToJson(inputFile);

        // Convertir el Object en JSON correctamente
        return objectMapper.convertValue(response, new TypeReference<List<Map<String, String>>>() {
        });
    }
}