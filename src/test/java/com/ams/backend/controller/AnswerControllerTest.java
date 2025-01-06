package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.service.AnswerService;

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

    private Answer answer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        answer = new Answer();
        answer.setId(1);
    }

    @Test
    public void testGetAllAnswers() {
        List<Answer> answers = Collections.singletonList(answer);
        when(answerService.getAllAnswers()).thenReturn(answers);

        List<Answer> result = answerController.getAllAnswers();

        assertEquals(answers, result);
        verify(answerService, times(1)).getAllAnswers();
    }

    @Test
    public void testGetAnswerById() throws Exception {
        when(answerService.getAnswerById(1)).thenReturn(answer);

        ResponseEntity<Answer> result = answerController.getAnswerById(1);

        assertEquals(answer, result.getBody());
        verify(answerService, times(1)).getAnswerById(1);
    }

    @Test
    public void testCreateAnswer() throws Exception {
        when(answerService.createAnswer(any(Answer.class))).thenReturn(answer);

        Answer result = answerController.createAnswer(answer);

        assertEquals(answer, result);
        verify(answerService, times(1)).createAnswer(any(Answer.class));
    }

    @Test
    public void testUpdateAnswer() throws Exception {
        Answer answer = new Answer();
        when(answerService.updateAnswer(eq(1), any(Answer.class))).thenReturn(answer);

        ResponseEntity<Answer> result = answerController.updateAnswer(1, answer);

        assertEquals(answer, result.getBody());
        verify(answerService, times(1)).updateAnswer(eq(1), any(Answer.class));

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
        List<Answer> answers = Collections.singletonList(answer);
        when(answerService.getAnswersByAuditType(1)).thenReturn(answers);

        List<Answer> result = answerController.getAnswersByAuditType(1);

        assertEquals(answers, result);
        verify(answerService, times(1)).getAnswersByAuditType(1);
    }
}
