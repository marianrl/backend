package com.ams.backend.controller;

import com.ams.backend.entity.AuditType;
import com.ams.backend.service.AuditTypeService;

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

    private AuditType auditType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        auditType = new AuditType();
        auditType.setId(1);
    }

    @Test
    public void getAllAuditTypesTest() throws Exception {

        List<AuditType> auditTypes = Collections.singletonList(auditType);
        when(auditTypeService.getAllAuditType()).thenReturn(auditTypes);

        List<AuditType> result = auditTypeController.getAllAuditType();

        assertEquals(auditTypes, result);
        verify(auditTypeService, times(1)).getAllAuditType();
    }

    @Test
    public void getAuditTypeByIdTest() throws Exception {
        when(auditTypeService.getAuditTypeById(1)).thenReturn(auditType);

        ResponseEntity<AuditType> result = auditTypeController.getAuditTypeById(1);

        assertEquals(auditType, result.getBody());
        verify(auditTypeService, times(1)).getAuditTypeById(1);
    }

    @Test
    public void createAuditTypeTest() throws Exception {
        when(auditTypeService.createAuditType(any(AuditType.class))).thenReturn(auditType);

        AuditType result = auditTypeController.createAuditType(auditType);

        assertEquals(auditType, result);
        verify(auditTypeService, times(1)).createAuditType(any(AuditType.class));
    }

    @Test
    public void updateAuditTypeTest() throws Exception {
        AuditType auditType = new AuditType();
        when(auditTypeService.updateAuditType(eq(1), any(AuditType.class))).thenReturn(auditType);

        ResponseEntity<AuditType> result = auditTypeController.updateAuditType(1, auditType);

        assertEquals(auditType, result.getBody());
        verify(auditTypeService, times(1)).updateAuditType(eq(1), any(AuditType.class));
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
