package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;

public interface AnswerService {
  List<Answer> getAllAnswers();
  Answer getAnswerById(int id) throws ResourceNotFoundException;
  List<Answer> getAnswersByAuditType(int id);
  Answer createAnswer(Answer answer);
  Answer updateAnswer(int id, Answer providedAnswer) throws ResourceNotFoundException;
  void deleteAnswer(int id) throws ResourceNotFoundException;
}
