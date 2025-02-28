package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    private AnswerServiceImpl answerService;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        answerService = new AnswerServiceImpl(answerRepository);
        answer = new Answer(1, "Yes"); 
    }

    @Test
    public void testGetAllAnswers() {
        List<Answer> answerList = Arrays.asList(answer);

        when(answerRepository.findAll()).thenReturn(answerList);

        List<Answer> result = answerService.getAllAnswers();

        assertEquals(1, result.size());
        assertEquals(answer.getId(), result.get(0).getId());
        verify(answerRepository, times(1)).findAll();
    }

    @Test
    public void testGetAnswerById_AnswerFound() throws ResourceNotFoundException {
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));

        Answer result = answerService.getAnswerById(1);

        assertNotNull(result);
        assertEquals(answer.getId(), result.getId());
        verify(answerRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAnswerById_AnswerNotFound() {
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> answerService.getAnswerById(999));

        verify(answerRepository, times(1)).findById(999);
    }

    @Test
    public void testGetAnswersByAuditType() {
        List<Answer> answerList = Arrays.asList(answer);

        when(answerRepository.findByAuditTypeId(1)).thenReturn(answerList);

        List<Answer> result = answerService.getAnswersByAuditType(1);

        assertEquals(1, result.size());
        assertEquals(answer.getId(), result.get(0).getId());
        verify(answerRepository, times(1)).findByAuditTypeId(1);
    }

    @Test
    public void testCreateAnswer() {
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer result = answerService.createAnswer(answer);

        assertNotNull(result);
        assertEquals(answer.getId(), result.getId());
        assertEquals(answer.getAnswer(), result.getAnswer());
        verify(answerRepository, times(1)).save(answer);
    }

    @Test
    public void testUpdateAnswer_AnswerFound() throws ResourceNotFoundException {
        Answer updatedAnswer = new Answer(1, "No");

        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(answerRepository.save(any(Answer.class))).thenReturn(updatedAnswer);

        Answer result = answerService.updateAnswer(1, updatedAnswer);

        assertNotNull(result);
        assertEquals(updatedAnswer.getAnswer(), result.getAnswer());
        verify(answerRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateAnswer_AnswerNotFound() {
        Answer updatedAnswer = new Answer(1, "No");

        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> answerService.updateAnswer(999, updatedAnswer));

        verify(answerRepository, times(1)).findById(999);
        verify(answerRepository, times(0)).save(updatedAnswer);  // No debe llamar a save
    }

    @Test
    public void testDeleteAnswer_AnswerFound() throws ResourceNotFoundException {
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        doNothing().when(answerRepository).deleteById(1);

        answerService.deleteAnswer(1);

        verify(answerRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAnswer_AnswerNotFound() {
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> answerService.deleteAnswer(999));

        verify(answerRepository, times(1)).findById(999);
        verify(answerRepository, times(0)).deleteById(999);
    }
}
