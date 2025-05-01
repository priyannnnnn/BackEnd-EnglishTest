package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Score;
import TestEnglishApi.TestEnglishApi.repositories.ScoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/scores")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173",  "http://139.162.6.202"})
public class ScoreController {
    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Score>> getAllScoresForUser(@PathVariable UUID userId){
        List<Score> scores = scoreRepository.findByUserId(userId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("section/{userId}")
    public ResponseEntity<Score> getUserScore(
            @PathVariable UUID userId,
            @RequestParam String sectionType
    ){
        Score score = scoreRepository.findByUserIdAndSectionType(userId, sectionType)
                .orElseThrow(()-> new RuntimeException("score not found"));
        return ResponseEntity.ok(score);
    }
}
