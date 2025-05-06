package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Vocabulary;
import TestEnglishApi.TestEnglishApi.service.VocabularyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth/vocabulary")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173",  "http://139.162.6.202"})
public class VocabularyController {

    private final VocabularyService service;

    public VocabularyController(VocabularyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Vocabulary> createQuestion(@RequestBody Vocabulary vocabulary){
        Vocabulary created = service.create(vocabulary);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/words")
    public ResponseEntity<List<Vocabulary>> getAllQuestions(){
        return ResponseEntity.ok(service.getAllQuestions());
    }

}
