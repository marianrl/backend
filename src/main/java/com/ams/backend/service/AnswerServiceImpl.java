package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AnswerMapper;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;
import com.ams.backend.service.interfaces.AnswerService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    public List<AnswerResponse> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(answerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AnswerResponse getAnswerById(int id) throws ResourceNotFoundException {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));
        return answerMapper.toResponse(answer);
    }

    public List<AnswerResponse> getAnswersByAuditType(int id) {
        return answerRepository.findByAuditTypeId(id).stream()
                .map(answerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AnswerResponse createAnswer(AnswerRequest answerRequest) {
        Answer answer = answerMapper.toEntity(answerRequest);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.toResponse(savedAnswer);
    }

    public AnswerResponse updateAnswer(int id, AnswerRequest answerRequest) throws ResourceNotFoundException {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));

        answer.setAnswer(answerRequest.getAnswer());
        Answer updatedAnswer = answerRepository.save(answer);
        return answerMapper.toResponse(updatedAnswer);
    }

    public void deleteAnswer(int id) throws ResourceNotFoundException {
        answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));

        answerRepository.deleteById(id);
    }
}
