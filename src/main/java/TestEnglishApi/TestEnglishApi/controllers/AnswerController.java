package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.dtos.AnswerDTO;
import TestEnglishApi.TestEnglishApi.entities.Answer;
import TestEnglishApi.TestEnglishApi.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/answers")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://139.162.6.202"})
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/{userId}")
    public ResponseEntity<List<Answer>> submitAnswers(@PathVariable UUID userId, @RequestBody List<AnswerDTO> answers) {
        return ResponseEntity.ok(answerService.submitAnswers(userId, answers));
    }
}

