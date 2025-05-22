package com.ams.backend.controller;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.CommonInputMapper;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.CommonInputResponse;
import com.ams.backend.service.interfaces.CommonInputService;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonInputControllerTest {

    @Mock
    private CommonInputService commonInputService;

    @Mock
    private CommonInputMapper mapper;

    @InjectMocks
    private CommonInputController commonInputController;

    private CommonInput commonInput;
    private CommonInputResponse commonInputResponse;
    private InputRequest inputRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        commonInput = new CommonInput();
        commonInput.setId(1);

        commonInputResponse = new CommonInputResponse();
        commonInputResponse.setId(1);

        inputRequest = new InputRequest();
        inputRequest.setLastName("Doe");
        inputRequest.setName("John");
        inputRequest.setCuil("20-12345678-9");
        inputRequest.setFile("12345");
        inputRequest.setAllocation("IT");
        inputRequest.setUoc("UOC1");
        inputRequest.setAdmissionDate(LocalDate.now());
        inputRequest.setClient(1);
        inputRequest.setBranch(1);
        inputRequest.setAuditId(1);

        commonInput.setLastName(inputRequest.getLastName());
        commonInput.setName(inputRequest.getName());
        commonInput.setCuil(inputRequest.getCuil());
        commonInput.setFile(inputRequest.getFile());
        commonInput.setAllocation(inputRequest.getAllocation());
        commonInput.setUoc(inputRequest.getUoc());
        commonInput.setAdmissionDate(inputRequest.getAdmissionDate());

        when(commonInputService.getMapper()).thenReturn(mapper);
        when(mapper.toResponse(any(CommonInput.class))).thenReturn(commonInputResponse);
    }

    @Test
    public void testGetAllCommonAudits() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getAllCommonInputs()).thenReturn(commonInputs);

        List<CommonInputResponse> result = commonInputController.getAllCommonInputs();

        assertEquals(Collections.singletonList(commonInputResponse), result);
        verify(commonInputService, times(1)).getAllCommonInputs();
    }

    @Test
    public void testGetCommonInputById() {
        when(commonInputService.getCommonInputById(1)).thenReturn(Optional.of(commonInput));

        Optional<CommonInputResponse> result = commonInputController.getCommonInputById(1);

        assertEquals(Optional.of(commonInputResponse), result);
        verify(commonInputService, times(1)).getCommonInputById(1);
    }

    @Test
    public void testGetCommonAuditByAuditNumber() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getCommonInputByAuditNumber(1)).thenReturn(commonInputs);

        ResponseEntity<List<CommonInputResponse>> result = commonInputController.getCommonAuditByAuditNumber(1);

        assertEquals(Collections.singletonList(commonInputResponse), result.getBody());
        verify(commonInputService, times(1)).getCommonInputByAuditNumber(1);
    }

    @Test
    public void testGetFilteredCommonInputs() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getFilteredCommonInputs(
                anyString(), anyString(), anyString(), anyString(), anyString(),
                anyLong(), anyString(), anyLong(), any(LocalDate.class), anyLong()))
                .thenReturn(commonInputs);

        List<CommonInputResponse> result = commonInputController.getFilteredCommonInputs(
                "Apellido", "Nombre", "20-12345678-9", "1234", "Asignacion",
                1L, "UOC", 1L, LocalDate.now(), 1L);

        assertEquals(Collections.singletonList(commonInputResponse), result);
        verify(commonInputService, times(1)).getFilteredCommonInputs(
                anyString(), anyString(), anyString(), anyString(), anyString(),
                anyLong(), anyString(), anyLong(), any(LocalDate.class), anyLong());
    }

    @Test
    public void testCreateCommonInput_Success() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);
        List<CommonInput> expectedCommonInputs = Collections.singletonList(commonInput);

        when(commonInputService.createCommonInputs(anyList())).thenReturn(expectedCommonInputs);

        List<CommonInput> result = commonInputController.createCommonInput(inputRequests);

        assertNotNull(result);
        assertEquals(expectedCommonInputs, result);
        verify(commonInputService).createCommonInputs(inputRequests);
    }

    @Test
    public void testCreateCommonInput_AuditNotFound() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);

        when(commonInputService.createCommonInputs(anyList()))
                .thenThrow(new ResourceNotFoundException("Audit not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputController.createCommonInput(inputRequests);
        });

        verify(commonInputService).createCommonInputs(inputRequests);
    }

    @Test
    public void testCreateCommonInput_FeaturesNotFound() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest);

        when(commonInputService.createCommonInputs(anyList()))
                .thenThrow(new ResourceNotFoundException("Features not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputController.createCommonInput(inputRequests);
        });

        verify(commonInputService).createCommonInputs(inputRequests);
    }

    @Test
    public void testUpdateCommonAudit() throws ResourceNotFoundException {
        CommonInputUpdateRequest commonInputUpdateRequest = new CommonInputUpdateRequest();
        when(commonInputService.updateCommonInput(eq(1), any(CommonInputUpdateRequest.class))).thenReturn(commonInput);

        ResponseEntity<CommonInput> result = commonInputController.updateCommonInput(1, commonInputUpdateRequest);

        assertEquals(commonInput, result.getBody());
        verify(commonInputService, times(1)).updateCommonInput(eq(1), any(CommonInputUpdateRequest.class));
    }

    @Test
    public void testDeleteCommonAudit() throws ResourceNotFoundException {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(commonInputService).deleteCommonInput(1);

        ResponseEntity<Void> result = commonInputController.deleteCommonInput(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(commonInputService, times(1)).deleteCommonInput(1);
    }

}
