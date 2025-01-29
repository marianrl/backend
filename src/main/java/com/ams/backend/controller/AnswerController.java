package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.interfaces.AnswerService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/answer")
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable(value = "id") int answerId)
            throws ResourceNotFoundException{
        Answer answer = answerService.getAnswerById(answerId);

        return ResponseEntity.ok().body(answer);
    }

    @PostMapping("/answer")
    public Answer createAnswer(@Valid @RequestBody Answer answer) {
        return answerService.createAnswer(answer);
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<Answer> updateAnswer(
            @PathVariable(value = "id") int answerId,
            @Valid @RequestBody Answer answerDetails) throws ResourceNotFoundException {
        final Answer updatedAnswer = answerService.updateAnswer(answerId, answerDetails);

        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable(value = "id") int answerId)
            throws ResourceNotFoundException {
        answerService.deleteAnswer(answerId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/answersByAuditType/{auditTypeId}")
    public List<Answer> getAnswersByAuditType(@PathVariable int auditTypeId){
        return answerService.getAnswersByAuditType(auditTypeId);
    }
}
