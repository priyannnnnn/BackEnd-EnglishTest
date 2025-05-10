package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.dtos.SubmitAnswerRequest;
import TestEnglishApi.TestEnglishApi.dtos.VocabularySubmitRequest;
import TestEnglishApi.TestEnglishApi.entities.Role;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.entities.Vocabulary;
import TestEnglishApi.TestEnglishApi.repositories.VocabularyRepository;
import TestEnglishApi.TestEnglishApi.service.VocabularyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/auth/vocabulary")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173",  "http://139.162.6.202"})
public class VocabularyController {

    private final VocabularyService service;
    private final VocabularyRepository vocabularyRepository;

    public VocabularyController(VocabularyService service, VocabularyRepository vocabularyRepository) {
        this.service = service;
        this.vocabularyRepository = vocabularyRepository;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@AuthenticationPrincipal User user, @RequestBody Vocabulary request){
        if (user.getRole() == Role.ADMIN){
            request.getWords().forEach(word -> word.setQuestion(request));
            Vocabulary saved = vocabularyRepository.save(request);
            return ResponseEntity.ok(saved);
        }else {
            return ResponseEntity.status(403).body("Unauthorized: Only admins can create questions.");
        }
    }

    @GetMapping
    public List<Vocabulary> getAll() {
        return service.getAllQuestions();
    }

    @PostMapping("/{questionId}/submit")
    public ResponseEntity<?> submitAnswer(@PathVariable UUID questionId, @RequestBody SubmitAnswerRequest request){
        Map<String, Object> result = service.submitAnswers(questionId, request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswers(@RequestBody VocabularySubmitRequest request){
        int score = service.SubmitAnswersAndcalculation(request);
        return ResponseEntity.ok(Map.of("score", score));
    }

}
