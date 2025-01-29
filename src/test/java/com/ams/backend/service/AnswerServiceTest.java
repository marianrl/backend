package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AnswerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    private AnswerServiceImpl answerService;

    @BeforeEach
    public void setup() {
        answerService = new AnswerServiceImpl(answerRepository);
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
        int answersId = 1;
        Answer expectedAnswer = new Answer(answersId, "CABA");

        Mockito.when(answerRepository.findById(answersId)).thenReturn(Optional.of(expectedAnswer));
        Answer actualAnswer = answerService.getAnswerById(answersId);

        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testGetAnswersByAuditType_Success() {
        int auditTypeId = 1;
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(new Answer(1, "Answer 1"));
        expectedAnswers.add(new Answer(2, "Answer 2"));

        Mockito.when(answerRepository.findByAuditTypeId(auditTypeId)).thenReturn(expectedAnswers);

        List<Answer> actualAnswers = answerService.getAnswersByAuditType(auditTypeId);

        assertEquals(expectedAnswers, actualAnswers);

        verify(answerRepository).findByAuditTypeId(auditTypeId);
    }

    @Test
    public void testCreateAnswer() {
        int answerId = 1;
        Answer answer = new Answer(answerId, "CABA");

        Mockito.when(answerRepository.save(answer)).thenReturn(answer);
        Answer actualAnswer = answerService.createAnswer(answer);

        assertEquals(actualAnswer, answer);
    }

    @Test
    public void testUpdateAnswer() throws ResourceNotFoundException {
        int answerId = 1;
        Answer answer = new Answer(answerId, "CABA");
        Answer updatedAnswer = new Answer(answerId, "GBA");

        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        Answer actualAnswer = answerService.updateAnswer(answerId, updatedAnswer);

        assertEquals(updatedAnswer.getId(), actualAnswer.getId());
        assertEquals(updatedAnswer.getAnswer(), actualAnswer.getAnswer());
    }

    @Test
    public void testDeleteAnswer() throws ResourceNotFoundException {
        int answerId = 1;
        Answer answer = new Answer(answerId, "CABA");

        Mockito.when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        answerService.deleteAnswer(answerId);

        verify(answerRepository).deleteById(1);
    }
}

