package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AnswerMapper;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;
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

    @Mock
    private AnswerMapper answerMapper;

    private AnswerServiceImpl answerService;
    private Answer answer;
    private AnswerResponse answerResponse;
    private AnswerRequest answerRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        answerService = new AnswerServiceImpl(answerRepository, answerMapper);
        answer = new Answer(1, "Yes");
        answerResponse = new AnswerResponse(1, "Yes");
        answerRequest = new AnswerRequest();
        answerRequest.setAnswer("Yes");
    }

    @Test
    public void testGetAllAnswers() {
        List<Answer> answerList = Arrays.asList(answer);

        when(answerRepository.findAll()).thenReturn(answerList);
        when(answerMapper.toResponse(answer)).thenReturn(answerResponse);

        List<AnswerResponse> result = answerService.getAllAnswers();

        assertEquals(1, result.size());
        assertEquals(answerResponse, result.get(0));
        verify(answerRepository, times(1)).findAll();
        verify(answerMapper, times(1)).toResponse(answer);
    }

    @Test
    public void testGetAnswerById_AnswerFound() throws ResourceNotFoundException {
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(answerMapper.toResponse(answer)).thenReturn(answerResponse);

        AnswerResponse result = answerService.getAnswerById(1);

        assertNotNull(result);
        assertEquals(answerResponse, result);
        verify(answerRepository, times(1)).findById(1);
        verify(answerMapper, times(1)).toResponse(answer);
    }

    @Test
    public void testGetAnswerById_AnswerNotFound() {
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> answerService.getAnswerById(999));

        verify(answerRepository, times(1)).findById(999);
        verify(answerMapper, never()).toResponse(any());
    }

    @Test
    public void testGetAnswersByAuditType() {
        List<Answer> answerList = Arrays.asList(answer);

        when(answerRepository.findByAuditTypeId(1)).thenReturn(answerList);
        when(answerMapper.toResponse(answer)).thenReturn(answerResponse);

        List<AnswerResponse> result = answerService.getAnswersByAuditType(1);

        assertEquals(1, result.size());
        assertEquals(answerResponse, result.get(0));
        verify(answerRepository, times(1)).findByAuditTypeId(1);
        verify(answerMapper, times(1)).toResponse(answer);
    }

    @Test
    public void testCreateAnswer() {
        when(answerMapper.toEntity(answerRequest)).thenReturn(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerMapper.toResponse(answer)).thenReturn(answerResponse);

        AnswerResponse result = answerService.createAnswer(answerRequest);

        assertNotNull(result);
        assertEquals(answerResponse, result);
        verify(answerMapper, times(1)).toEntity(answerRequest);
        verify(answerRepository, times(1)).save(answer);
        verify(answerMapper, times(1)).toResponse(answer);
    }

    @Test
    public void testUpdateAnswer_AnswerFound() throws ResourceNotFoundException {
        Answer updatedAnswer = new Answer(1, "No");
        AnswerResponse updatedResponse = new AnswerResponse(1, "No");

        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(answerRepository.save(any(Answer.class))).thenReturn(updatedAnswer);
        when(answerMapper.toResponse(updatedAnswer)).thenReturn(updatedResponse);

        AnswerResponse result = answerService.updateAnswer(1, answerRequest);

        assertNotNull(result);
        assertEquals(updatedResponse, result);
        verify(answerRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).save(any(Answer.class));
        verify(answerMapper, times(1)).toResponse(updatedAnswer);
    }

    @Test
    public void testUpdateAnswer_AnswerNotFound() {
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> answerService.updateAnswer(999, answerRequest));

        verify(answerRepository, times(1)).findById(999);
        verify(answerRepository, never()).save(any());
        verify(answerMapper, never()).toResponse(any());
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
        verify(answerRepository, never()).deleteById(999);
    }
}
