package com.ams.backend.controller;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.service.interfaces.AfipInputService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AfipInputControllerTest {

    @Mock
    private AfipInputService afipInputService;

    @InjectMocks
    private AfipInputController afipInputController;

    private AfipInput afipInput;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        afipInput = new AfipInput();
        afipInput.setId(1);
    }

    @Test
    public void testGetAllAfipAudits() {
        List<AfipInput> afipInputs = Collections.singletonList(afipInput);
        when(afipInputService.getAllAfipInputs()).thenReturn(afipInputs);

        List<AfipInput> result = afipInputController.getAllAfipAudits();

        assertEquals(afipInputs, result);
        verify(afipInputService, times(1)).getAllAfipInputs();
    }

    @Test
    public void testGetAfipInputById() {
        when(afipInputService.getAfipInputById(1)).thenReturn(Optional.of(afipInput));

        Optional<AfipInput> result = afipInputController.getAfipInputById(1);

        assertEquals(Optional.of(afipInput), result);
        verify(afipInputService, times(1)).getAfipInputById(1);
    }

    @Test
    public void testGetAfipAuditByAuditNumber() {
        List<AfipInput> afipInputs = Collections.singletonList(afipInput);
        when(afipInputService.getAfipInputByAuditNumber(1)).thenReturn(afipInputs);

        ResponseEntity<List<AfipInput>> result = afipInputController.getAfipAuditByAuditNumber(1);

        assertEquals(afipInputs, result.getBody());
        verify(afipInputService, times(1)).getAfipInputByAuditNumber(1);
    }

    @Test
    public void testCreateAfipAudit() {
        when(afipInputService.createAfipInput(any(AfipInput.class))).thenReturn(afipInput);

        AfipInput result = afipInputController.createAfipAudit(afipInput);

        assertEquals(afipInput, result);
        verify(afipInputService, times(1)).createAfipInput(any(AfipInput.class));
    }

    @Test
    public void testUpdateAfipAudit() throws ResourceNotFoundException {
        AfipInputUpdateRequest afipInputUpdateRequest = new AfipInputUpdateRequest();
        when(afipInputService.updateAfipInput(eq(1), any(AfipInputUpdateRequest.class))).thenReturn(afipInput);

        ResponseEntity<AfipInput> result = afipInputController.updateAfipAudit(1, afipInputUpdateRequest);

        assertEquals(afipInput, result.getBody());
        verify(afipInputService, times(1)).updateAfipInput(eq(1), any(AfipInputUpdateRequest.class));
    }

    @Test
    public void testDeleteAfipAudit() throws ResourceNotFoundException {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(afipInputService).deleteAfipInput(1);

        ResponseEntity<Void> result = afipInputController.deleteAfipAudit(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(afipInputService, times(1)).deleteAfipInput(1);
    }
}
