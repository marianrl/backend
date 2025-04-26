package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;

public interface AnswerService {
  List<AnswerResponse> getAllAnswers();

  AnswerResponse getAnswerById(int id) throws ResourceNotFoundException;

  List<AnswerResponse> getAnswersByAuditType(int id);

  AnswerResponse createAnswer(AnswerRequest answer);

  AnswerResponse updateAnswer(int id, AnswerRequest answerDetails) throws ResourceNotFoundException;

  void deleteAnswer(int id) throws ResourceNotFoundException;
}
