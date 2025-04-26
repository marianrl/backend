package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AnswerRequest;
import com.ams.backend.response.AnswerResponse;
import com.ams.backend.service.interfaces.AnswerService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/answer")
    public List<AnswerResponse> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<AnswerResponse> getAnswerById(@PathVariable(value = "id") int answerId)
            throws ResourceNotFoundException {
        AnswerResponse answer = answerService.getAnswerById(answerId);
        return ResponseEntity.ok().body(answer);
    }

    @PostMapping("/answer")
    public AnswerResponse createAnswer(@Valid @RequestBody AnswerRequest answer) {
        return answerService.createAnswer(answer);
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable(value = "id") int answerId,
            @Valid @RequestBody AnswerRequest answerDetails) throws ResourceNotFoundException {
        AnswerResponse updatedAnswer = answerService.updateAnswer(answerId, answerDetails);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable(value = "id") int answerId)
            throws ResourceNotFoundException {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/answersByAuditType/{auditTypeId}")
    public List<AnswerResponse> getAnswersByAuditType(@PathVariable int auditTypeId) {
        return answerService.getAnswersByAuditType(auditTypeId);
    }
}
