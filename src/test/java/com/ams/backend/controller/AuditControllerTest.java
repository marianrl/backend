package com.ams.backend.controller;

import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.response.AuditedResponse;
import com.ams.backend.service.interfaces.AuditService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuditControllerTest {

    @Mock
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    private AuditResponse auditResponse;
    private AuditRequest auditRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        AuditTypeResponse auditTypeResponse = new AuditTypeResponse(1, "Financial Audit");
        AuditedResponse auditedResponse = new AuditedResponse(2, "No");

        auditResponse = new AuditResponse(1, LocalDate.now(), auditTypeResponse, auditedResponse);
        auditRequest = new AuditRequest(1);
    }

    @Test
    public void getAllAuditTest() throws Exception {
        List<AuditResponse> audits = Collections.singletonList(auditResponse);
        when(auditService.getAllAudit()).thenReturn(audits);

        List<AuditResponse> result = auditController.getAllAudits();

        assertEquals(audits, result);
        verify(auditService, times(1)).getAllAudit();
    }

    @Test
    public void getAuditByIdTest() throws Exception {
        when(auditService.getAuditById(1)).thenReturn(auditResponse);

        ResponseEntity<AuditResponse> result = auditController.getAuditById(1);

        assertEquals(auditResponse, result.getBody());
        verify(auditService, times(1)).getAuditById(1);
    }

    @Test
    public void createAuditTest() throws Exception {
        when(auditService.createAudit(any(AuditRequest.class))).thenReturn(auditResponse);

        AuditResponse result = auditController.createAudit(auditRequest);

        assertEquals(auditResponse, result);
        verify(auditService, times(1)).createAudit(any(AuditRequest.class));
    }

    @Test
    public void updateAuditTest() throws Exception {
        when(auditService.updateAudit(eq(1), any(AuditRequest.class))).thenReturn(auditResponse);

        ResponseEntity<AuditResponse> result = auditController.updateAudit(1, auditRequest);

        assertEquals(auditResponse, result.getBody());
        verify(auditService, times(1)).updateAudit(1, auditRequest);
    }

    @Test
    public void deleteAuditTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(auditService).deleteAudit(1);

        ResponseEntity<Void> result = auditController.deleteAudit(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(auditService, times(1)).deleteAudit(1);
    }
}
