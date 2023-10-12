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
public class AuditServiceTest {

    @Mock
    private AnswerRepository auditRepository;

    private AnswerService auditService;

    @Before
    public void setup() {
        auditService = new AnswerService(auditRepository);
    }

    @Test
    public void testGetAllAnswers() {
        List<Answer> audits = new ArrayList<>();
        Mockito.when(auditRepository.findAll()).thenReturn(audits);
        List<Answer> actualAnswers = auditService.getAllAnswers();

        assertEquals(audits, actualAnswers);
    }

    @Test
    public void testGetAnswersById() throws ResourceNotFoundException {
        int auditsId = 1;
        Answer expectedAnswer = new Answer(auditsId, "CABA");

        Mockito.when(auditRepository.findById(auditsId)).thenReturn(Optional.of(expectedAnswer));
        Answer actualAnswer = auditService.getAnswerById(auditsId);

        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testCreateAnswer() {
        int auditId = 1;
        Answer audit = new Answer(auditId, "CABA");

        Mockito.when(auditRepository.save(audit)).thenReturn(audit);
        Answer actualAnswer = auditService.createAnswer(audit);

        assertEquals(actualAnswer, audit);
    }

    @Test
    public void testUpdateAnswer() throws ResourceNotFoundException {
        int auditId = 1;
        Answer audit = new Answer(auditId, "CABA");
        Answer updatedAnswer = new Answer(auditId, "GBA");

        Mockito.when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        Answer actualAnswer = auditService.updateAnswer(auditId, updatedAnswer);

        assertEquals(updatedAnswer.getId(), actualAnswer.getId());
        assertEquals(updatedAnswer.getAnswer(), actualAnswer.getAnswer());
    }

    @Test
    public void testDeleteAnswer() throws ResourceNotFoundException {
        int auditId = 1;
        Answer audit = new Answer(auditId, "CABA");

        Mockito.when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        auditService.deleteAnswer(auditId);

        verify(auditRepository).deleteById(1);
    }
}

