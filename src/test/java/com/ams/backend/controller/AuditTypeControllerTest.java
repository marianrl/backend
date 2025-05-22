package com.ams.backend.controller;

import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.service.interfaces.AuditTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

public class AuditTypeControllerTest {

    @Mock
    private AuditTypeService auditTypeService;

    @InjectMocks
    private AuditTypeController auditTypeController;

    private AuditTypeResponse auditTypeResponse;
    private AuditTypeRequest auditTypeRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        auditTypeResponse = new AuditTypeResponse(1, "Test Audit Type");
        auditTypeRequest = new AuditTypeRequest(1, "Test Audit Type");
    }

    @Test
    public void getAllAuditTypesTest() throws Exception {
        List<AuditTypeResponse> auditTypes = Collections.singletonList(auditTypeResponse);
        when(auditTypeService.getAllAuditType()).thenReturn(auditTypes);

        List<AuditTypeResponse> result = auditTypeController.getAllAuditType();

        assertEquals(auditTypes, result);
        verify(auditTypeService, times(1)).getAllAuditType();
    }

    @Test
    public void getAuditTypeByIdTest() throws Exception {
        when(auditTypeService.getAuditTypeById(1)).thenReturn(auditTypeResponse);

        ResponseEntity<AuditTypeResponse> result = auditTypeController.getAuditTypeById(1);

        assertEquals(auditTypeResponse, result.getBody());
        verify(auditTypeService, times(1)).getAuditTypeById(1);
    }

    @Test
    public void createAuditTypeTest() throws Exception {
        when(auditTypeService.createAuditType(any(AuditTypeRequest.class))).thenReturn(auditTypeResponse);

        AuditTypeResponse result = auditTypeController.createAuditType(auditTypeRequest);

        assertEquals(auditTypeResponse, result);
        verify(auditTypeService, times(1)).createAuditType(any(AuditTypeRequest.class));
    }

    @Test
    public void updateAuditTypeTest() throws Exception {
        when(auditTypeService.updateAuditType(eq(1), any(AuditTypeRequest.class))).thenReturn(auditTypeResponse);

        ResponseEntity<AuditTypeResponse> result = auditTypeController.updateAuditType(1, auditTypeRequest);

        assertEquals(auditTypeResponse, result.getBody());
        verify(auditTypeService, times(1)).updateAuditType(eq(1), any(AuditTypeRequest.class));
    }

    @Test
    public void deleteAuditTypeTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(auditTypeService).deleteAuditType(1);

        ResponseEntity<Void> result = auditTypeController.deleteAuditType(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(auditTypeService, times(1)).deleteAuditType(1);
    }
}
