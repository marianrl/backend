package com.ams.backend.controller;

import com.ams.backend.entity.Audited;
import com.ams.backend.service.AuditedService;

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

public class AuditedControllerTest {

    @Mock
    private AuditedService auditedService;

    @InjectMocks
    private AuditedController auditedController;

    private Audited audited;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        audited = new Audited();
        audited.setId(1);
    }


    @Test
    public void getAllAuditedTest() throws Exception {
        List<Audited> auditedList = Collections.singletonList(audited);
        when(auditedService.getAllAudited()).thenReturn(auditedList);

        List<Audited> result = auditedController.getAllAudited();

        assertEquals(auditedList, result);
        verify(auditedService, times(1)).getAllAudited();
    }

    @Test
    public void getAuditedByIdTest() throws Exception {
        when(auditedService.getAuditedById(1)).thenReturn(audited);

        ResponseEntity<Audited> result = auditedController.getAuditedById(1);

        assertEquals(audited, result.getBody());
        verify(auditedService, times(1)).getAuditedById(1);
    }

    @Test
    public void createAuditedTest() throws Exception {
        when(auditedService.createAudited(any(Audited.class))).thenReturn(audited);

        Audited result = auditedController.createAudited(audited);

        assertEquals(audited, result);
        verify(auditedService, times(1)).createAudited(any(Audited.class));
    }

    @Test
    public void updateAuditedTest() throws Exception {
        Audited audited = new Audited();
        when(auditedService.updateAudited(eq(1), any(Audited.class))).thenReturn(audited);

        ResponseEntity<Audited> result = auditedController.updateAudited(1, audited);

        assertEquals(audited, result.getBody());
        verify(auditedService, times(1)).updateAudited(eq(1), any(Audited.class));
    }

    @Test
    public void deleteAuditedTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(auditedService).deleteAudited(1);

        ResponseEntity<Void> result = auditedController.deleteAudited(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(auditedService, times(1)).deleteAudited(1);
    }

}
