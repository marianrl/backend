package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.*;
import com.ams.backend.service.interfaces.AfipInputService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AfipInputControllerTest {

    @Mock
    private AfipInputService afipInputService;

    @InjectMocks
    private AfipInputController afipInputController;

    private AfipInputResponse afipInputResponse;
    private InputRequest inputRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        afipInputResponse = new AfipInputResponse(
                1,
                "Perez",
                "Juan",
                "20-45125484-7",
                "4568",
                "1248",
                new ClientResponse(1, "hola"),
                "Capital Federal",
                new BranchResponse(1, "hola"),
                LocalDate.now(),
                new FeaturesResponse(1, new AuditTypeResponse(1, "INTERNA"), new AnswerResponse(1, "SE AJUSTA")),
                new AuditResponse(1, LocalDate.now(), new AuditTypeResponse(1, "INTERNA"),
                        new AuditedResponse(1, "NO")));

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
        List<AfipInputResponse> afipInputs = Collections.singletonList(afipInputResponse);
        when(afipInputService.getAllAfipInputs()).thenReturn(afipInputs);

        List<AfipInputResponse> result = afipInputController.getAllAfipInputs();

        assertEquals(afipInputs, result);
        verify(afipInputService, times(1)).getAllAfipInputs();
    }

    @Test
    public void testGetAfipInputById() {
        when(afipInputService.getAfipInputById(1)).thenReturn(Optional.of(afipInputResponse));

        Optional<AfipInputResponse> result = afipInputController.getAfipInputById(1);

        assertEquals(Optional.of(afipInputResponse), result);
        verify(afipInputService, times(1)).getAfipInputById(1);
    }

    @Test
    public void testGetAfipAuditByAuditNumber() {
        List<AfipInputResponse> afipInputs = Collections.singletonList(afipInputResponse);
        when(afipInputService.getAfipInputByAuditNumber(1)).thenReturn(afipInputs);

        ResponseEntity<List<AfipInputResponse>> result = afipInputController.getAfipInputByAuditNumber(1);

        assertEquals(afipInputs, result.getBody());
        verify(afipInputService, times(1)).getAfipInputByAuditNumber(1);
    }

    @Test
    public void testCreateAfipInput() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);
        List<AfipInputResponse> expectedAfipInputs = Collections.singletonList(afipInputResponse);

        when(afipInputService.createAfipInputs(anyList())).thenReturn(expectedAfipInputs);

        List<AfipInputResponse> result = afipInputController.createAfipInput(inputRequests);

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
        when(afipInputService.updateAfipInput(eq(1), any(AfipInputUpdateRequest.class))).thenReturn(afipInputResponse);

        ResponseEntity<AfipInputResponse> result = afipInputController.updateAfipInput(1, afipInputUpdateRequest);

        assertEquals(afipInputResponse, result.getBody());
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

    @Test
    void getFilteredAfipInputs_Success() {
        // Arrange
        List<AfipInputResponse> expectedInputs = Arrays.asList(afipInputResponse, afipInputResponse);
        when(afipInputService.getFilteredAfipInputs(any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any()))
                .thenReturn(expectedInputs);

        // Act
        List<AfipInputResponse> result = afipInputController.getFilteredAfipInputs(
                "Doe", "John", "123", "456", "ASG", 1L, "UOC", 2L, LocalDate.now(), 3L);

        // Assert
        assertEquals(expectedInputs, result);
        verify(afipInputService).getFilteredAfipInputs(
                "Doe", "John", "123", "456", "ASG", 1L, "UOC", 2L, LocalDate.now(), 3L);
    }
}
