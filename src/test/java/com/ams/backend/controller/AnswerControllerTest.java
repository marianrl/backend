package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.service.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void testGetAllAnswers() throws Exception {
        List<Answer> answers = new ArrayList<>();
        // Agregar respuestas de prueba
        when(answerService.getAllAnswers()).thenReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/answer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(answers.size()));

        verify(answerService, times(1)).getAllAnswers();
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void testGetAnswerById() throws Exception {
        int answerId = 1;
        Answer answer = new Answer(answerId, "CABA");
        when(answerService.getAnswerById(answerId)).thenReturn(answer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/answer/{id}", answerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(answer.getId()));

        verify(answerService, times(1)).getAnswerById(answerId);
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void testCreateAnswer() throws Exception {
        Answer answer = new Answer(1, "CABA");
        when(answerService.createAnswer(ArgumentMatchers.any(Answer.class))).thenReturn(answer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"answer\": \"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(answer.getId()));

        verify(answerService, times(1)).createAnswer(ArgumentMatchers.any(Answer.class));
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void testUpdateAnswer() throws Exception {
        int answerId = 1;
        Answer updatedAnswer = new Answer(answerId, "GBA");

        when(answerService.updateAnswer(eq(answerId), ArgumentMatchers.any(Answer.class))).thenReturn(updatedAnswer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/answer/{id}", answerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"answer\": \"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(answerService, times(1)).updateAnswer(eq(answerId), ArgumentMatchers.any(Answer.class));
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void testDeleteAnswer() throws Exception {
        int answerId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/answer/{id}", answerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(answerService, times(1)).deleteAnswer(answerId);
        verifyNoMoreInteractions(answerService);
    }

    @Test
    public void testGetAnswersByAuditType() throws Exception {
        int auditTypeId = 1;
        List<Answer> answers = new ArrayList<>();
        // Agregar respuestas de prueba
        when(answerService.getAnswersByAuditType(auditTypeId)).thenReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/answersByAuditType/{auditTypeId}", auditTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(answers.size()));

        verify(answerService, times(1)).getAnswersByAuditType(auditTypeId);
        verifyNoMoreInteractions(answerService);
    }
}
