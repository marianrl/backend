package com.ams.backend.controller;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.service.interfaces.AfipInputService;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AfipInputControllerTest {

    @Mock
    private AfipInputService afipInputService;

    @InjectMocks
    private AfipInputController afipInputController;

    private AfipInput afipInput;
    private InputRequest inputRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        afipInput = new AfipInput();
        afipInput.setId(1);

        inputRequest = new InputRequest();
        inputRequest.setLastName("Perez");
        inputRequest.setName("Juan");
        inputRequest.setCuil("20-45125484-7");
        inputRequest.setFile("4568");
        inputRequest.setAllocation("1248");
        inputRequest.setClient(1);
        inputRequest.setUoc("Capital Federal");
        inputRequest.setBranch(1);
        inputRequest.setAuditId(1);
        inputRequest.setAdmissionDate(LocalDate.now());
    }

    @Test
    public void testGetAllAfipAudits() {
        List<AfipInput> afipInputs = Collections.singletonList(afipInput);
        when(afipInputService.getAllAfipInputs()).thenReturn(afipInputs);

        List<AfipInput> result = afipInputController.getAllAfipInputs();

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

        ResponseEntity<List<AfipInput>> result = afipInputController.getAfipInputByAuditNumber(1);

        assertEquals(afipInputs, result.getBody());
        verify(afipInputService, times(1)).getAfipInputByAuditNumber(1);
    }

    @Test
    public void testCreateAfipInput() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);
        List<AfipInput> expectedAfipInputs = Collections.singletonList(afipInput);

        when(afipInputService.createAfipInputs(anyList())).thenReturn(expectedAfipInputs);

        List<AfipInput> result = afipInputController.createAfipInput(inputRequests);

        assertEquals(expectedAfipInputs, result);
        verify(afipInputService, times(1)).createAfipInputs(anyList());
    }

    @Test
    public void testCreateAfipInput_ResourceNotFound() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);

        when(afipInputService.createAfipInputs(anyList()))
                .thenThrow(new ResourceNotFoundException("Resource not found"));

        assertThrows(ResourceNotFoundException.class, () -> afipInputController.createAfipInput(inputRequests));
        verify(afipInputService, times(1)).createAfipInputs(anyList());
    }

    @Test
    public void testUpdateAfipAudit() throws ResourceNotFoundException {
        AfipInputUpdateRequest afipInputUpdateRequest = new AfipInputUpdateRequest();
        when(afipInputService.updateAfipInput(eq(1), any(AfipInputUpdateRequest.class))).thenReturn(afipInput);

        ResponseEntity<AfipInput> result = afipInputController.updateAfipInput(1, afipInputUpdateRequest);

        assertEquals(afipInput, result.getBody());
        verify(afipInputService, times(1)).updateAfipInput(eq(1), any(AfipInputUpdateRequest.class));
    }

    @Test
    public void testDeleteAfipAudit() throws ResourceNotFoundException {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(afipInputService).deleteAfipInput(1);

        ResponseEntity<Void> result = afipInputController.deleteAfipInput(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(afipInputService, times(1)).deleteAfipInput(1);
    }
}
