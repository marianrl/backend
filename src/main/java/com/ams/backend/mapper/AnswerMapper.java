package com.ams.backend.mapper;

import com.ams.backend.entity.Answer;
import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

  public Answer toEntity(AnswerRequest request) {
    Answer answer = new Answer();
    answer.setAnswer(request.getAnswer());
    return answer;
  }

  public AnswerResponse toResponse(Answer entity) {
    return new AnswerResponse(
        entity.getId(),
        entity.getAnswer());
  }
}