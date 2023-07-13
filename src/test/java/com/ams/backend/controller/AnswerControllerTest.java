package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.service.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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

@RunWith(SpringRunner.class)
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @Test
    public void getAllAnswersTest() throws Exception {

        List<Answer> answers = new ArrayList<>();
        Mockito.when(answerService.getAllAnswers()).thenReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/answer"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAnswerByIdTest() throws Exception {
        Answer answer = new Answer(1, "CABA");
        Mockito.when(answerService.getAnswerById(1)).thenReturn(answer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/answer/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answer").value("CABA"));
    }

    @Test
    public void createAnswerTest() throws Exception {
        Answer savedAnswer = new Answer(1, "CABA");
        Mockito.when(answerService.createAnswer(savedAnswer)).thenReturn(savedAnswer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateAnswerTest() throws Exception {
        Answer updatedAnswer = new Answer(1, "GBA");

        Mockito.when(answerService.updateAnswer(1, updatedAnswer)).thenReturn(updatedAnswer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/answer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"answer\":\"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(answerService, Mockito.times(1))
                .updateAnswer(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Answer.class));
    }

    @Test
    public void deleteAnswerTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/answer/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(answerService, Mockito.times(1)).deleteAnswer(id);
    }

}
