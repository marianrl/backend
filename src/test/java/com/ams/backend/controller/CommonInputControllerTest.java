package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.service.CommonInputService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommonInputControllerTest {

    @Mock
    private CommonInputService commonInputService;

    @InjectMocks
    private CommonInputController commonInputController;

    private CommonInput commonInput;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        commonInput = new CommonInput();
        commonInput.setId(1);
    }

    @Test
    public void testGetAllCommonAudits() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getAllCommonInputs()).thenReturn(commonInputs);

        List<CommonInput> result = commonInputController.getAllCommonInputs();

        assertEquals(commonInputs, result);
        verify(commonInputService, times(1)).getAllCommonInputs();
    }

    @Test
    public void testGetCommonInputById() {
        when(commonInputService.getCommonInputById(1)).thenReturn(Optional.of(commonInput));

        Optional<CommonInput> result = commonInputController.getCommonInputById(1);

        assertEquals(Optional.of(commonInput), result);
        verify(commonInputService, times(1)).getCommonInputById(1);
    }

    @Test
    public void testGetCommonAuditByAuditNumber() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getCommonInputByAuditNumber(1)).thenReturn(commonInputs);

        ResponseEntity<List<CommonInput>> result = commonInputController.getCommonAuditByAuditNumber(1);

        assertEquals(commonInputs, result.getBody());
        verify(commonInputService, times(1)).getCommonInputByAuditNumber(1);
    }

    @Test
    public void testGetFilteredCommonInputs() {
        List<CommonInput> commonInputs = Collections.singletonList(commonInput);
        when(commonInputService.getFilteredCommonInputs(
                anyString(), anyString(), anyString(), anyString(), anyString(),
                anyLong(), anyString(), anyLong(), any(LocalDate.class), anyLong()))
                .thenReturn(commonInputs);

        List<CommonInput> result = commonInputController.getFilteredCommonInputs(
                "Apellido", "Nombre", "20-12345678-9", "1234", "Asignacion",
                1L, "UOC", 1L, LocalDate.now(), 1L);

        assertEquals(commonInputs, result);
        verify(commonInputService, times(1)).getFilteredCommonInputs(
                anyString(), anyString(), anyString(), anyString(), anyString(),
                anyLong(), anyString(), anyLong(), any(LocalDate.class), anyLong());
    }

    @Test
    public void testCreateCommonAudit() {
        when(commonInputService.createCommonInput(any(CommonInput.class))).thenReturn(commonInput);

        CommonInput result = commonInputController.createCommonInput(commonInput);

        assertEquals(commonInput, result);
        verify(commonInputService, times(1)).createCommonInput(any(CommonInput.class));
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
