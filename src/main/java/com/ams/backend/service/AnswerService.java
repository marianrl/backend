package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswerById(int id) throws ResourceNotFoundException {
        return answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(int id, Answer providedAnswer) throws ResourceNotFoundException {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));

        answer.setAnswer(providedAnswer.getAnswer());
        answerRepository.save(answer);

        return answer;
    }

    public void deleteAnswer(int id) throws ResourceNotFoundException{
        answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found for this id :: " + id));

        answerRepository.deleteById(id);
    }
}
