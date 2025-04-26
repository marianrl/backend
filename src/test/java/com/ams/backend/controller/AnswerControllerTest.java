package com.ams.backend.controller;

import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;
import com.ams.backend.service.interfaces.AnswerService;

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
import static org.mockito.Mockito.*;

public class AnswerControllerTest {

    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    private AnswerResponse answerResponse;
    private AnswerRequest answerRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        answerResponse = new AnswerResponse(1, "Test Answer");
        answerRequest = new AnswerRequest();
        answerRequest.setAnswer("Test Answer");
    }

    @Test
    public void testGetAllAnswers() {
        List<AnswerResponse> answers = Collections.singletonList(answerResponse);
        when(answerService.getAllAnswers()).thenReturn(answers);

        List<AnswerResponse> result = answerController.getAllAnswers();

        assertEquals(answers, result);
        verify(answerService, times(1)).getAllAnswers();
    }

    @Test
    public void testGetAnswerById() throws Exception {
        when(answerService.getAnswerById(1)).thenReturn(answerResponse);

        ResponseEntity<AnswerResponse> result = answerController.getAnswerById(1);

        assertEquals(answerResponse, result.getBody());
        verify(answerService, times(1)).getAnswerById(1);
    }

    @Test
    public void testCreateAnswer() throws Exception {
        when(answerService.createAnswer(any(AnswerRequest.class))).thenReturn(answerResponse);

        AnswerResponse result = answerController.createAnswer(answerRequest);

        assertEquals(answerResponse, result);
        verify(answerService, times(1)).createAnswer(any(AnswerRequest.class));
    }

    @Test
    public void testUpdateAnswer() throws Exception {
        when(answerService.updateAnswer(eq(1), any(AnswerRequest.class))).thenReturn(answerResponse);

        ResponseEntity<AnswerResponse> result = answerController.updateAnswer(1, answerRequest);

        assertEquals(answerResponse, result.getBody());
        verify(answerService, times(1)).updateAnswer(eq(1), any(AnswerRequest.class));
    }

    @Test
    public void testDeleteAnswer() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(answerService).deleteAnswer(1);

        ResponseEntity<Void> result = answerController.deleteAnswer(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(answerService, times(1)).deleteAnswer(1);
    }

    @Test
    public void testGetAnswersByAuditType() throws Exception {
        List<AnswerResponse> answers = Collections.singletonList(answerResponse);
        when(answerService.getAnswersByAuditType(1)).thenReturn(answers);

        List<AnswerResponse> result = answerController.getAnswersByAuditType(1);

        assertEquals(answers, result);
        verify(answerService, times(1)).getAnswersByAuditType(1);
    }
}
