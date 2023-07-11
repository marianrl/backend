package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AnswerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    private AnswerService answerService;

    @Before
    public void setup() {
        answerService = new AnswerService(answerRepository);
    }

    @Test
    public void testGetAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        Mockito.when(answerRepository.findAll()).thenReturn(answers);
        List<Answer> actualAnswers = answerService.getAllAnswers();

        assertEquals(answers, actualAnswers);
    }

    @Test
    public void testGetAnswersById() throws ResourceNotFoundException {
        Long answersId = 1L;
        Answer expectedAnswer = new Answer(answersId, "CABA");

        Mockito.when(answerRepository.findById(answersId)).thenReturn(Optional.of(expectedAnswer));
        Answer actualAnswer = answerService.getAnswerById(answersId);

        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testCreateAnswer() {
        Long answerId = 1L;
        Answer answer = new Answer(answerId, "CABA");

        Mockito.when(answerRepository.save(answer)).thenReturn(answer);
        Answer actualAnswer = answerService.createAnswer(answer);

        assertEquals(actualAnswer, answer);
    }

    @Test
    public void testUpdateAnswer() throws ResourceNotFoundException {
        Long answerId = 1L;
        Answer answer = new Answer(answerId, "CABA");
        Answer updatedAnswer = new Answer(answerId, "GBA");

        Mockito.when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        Answer actualAnswer = answerService.updateAnswer(answerId, updatedAnswer);

        assertEquals(updatedAnswer.getId(), actualAnswer.getId());
        assertEquals(updatedAnswer.getAnswer(), actualAnswer.getAnswer());
    }

    @Test
    public void testDeleteAnswer() throws ResourceNotFoundException {
        Long answerId = 1L;
        Answer answer = new Answer(answerId, "CABA");

        Mockito.when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        answerService.deleteAnswer(answerId);

        verify(answerRepository).deleteById(1L);
    }
}

