package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.service.interfaces.AuditService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    private Audit audit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        audit = new Audit();
        audit.setId(1);
    }

    @Test
    public void getAllAuditTest() throws Exception {
        List<Audit> audits = Collections.singletonList(audit);
        when(auditService.getAllAudit()).thenReturn(audits);

        List<Audit> result = auditController.getAllAudits();

        assertEquals(audits, result);
        verify(auditService, times(1)).getAllAudit();
    }

    @Test
    public void getAuditByIdTest() throws Exception {
        when(auditService.getAuditById(1)).thenReturn(audit);

        ResponseEntity<Audit> result = auditController.getAuditById(1);

        assertEquals(audit, result.getBody());
        verify(auditService, times(1)).getAuditById(1);
    }

    @Test
    public void createAuditTest() throws Exception {

        when(auditService.createAudit(1)).thenReturn(audit);

        Audit result = auditController.createAudit(audit.getId());

        assertEquals(audit, result);
        verify(auditService, times(1)).createAudit(1);
    }

    @Test
    public void updateAuditTest() throws Exception {

        when(auditService.updateAudit(eq(1), any(Audit.class))).thenReturn(audit);

        ResponseEntity<Audit> result = auditController.updateAudit(1, audit);

        assertEquals(audit, result.getBody());
        verify(auditService, times(1)).updateAudit(1, audit);
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
