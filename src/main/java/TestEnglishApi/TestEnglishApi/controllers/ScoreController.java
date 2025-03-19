package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Score;
import TestEnglishApi.TestEnglishApi.repositories.ScoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth/scores")
public class ScoreController {
    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Score>GetUserScore(@PathVariable UUID userId){
        Score score = scoreRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Score not found"));
        return ResponseEntity.ok(score);
    }
}
