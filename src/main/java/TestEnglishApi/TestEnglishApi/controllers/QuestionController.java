package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.entities.Role;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.repositories.QuestionRepository;
import TestEnglishApi.TestEnglishApi.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/questions/grammar")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173",  "http://139.162.6.202"})
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionService questionService, QuestionRepository questionRepository){
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/reading")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<?>>getReadingQuestions(@AuthenticationPrincipal User user){
        if(user.getRole() == Role.ADMIN){
            return ResponseEntity.ok(questionService.getReadingQuestions());
        }
        else {
            return ResponseEntity.status(403).body(Collections.singletonList("Unauthorized: Only admins can create questions."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@AuthenticationPrincipal User user, @RequestBody Question question) {

        if (user.getRole() == Role.ADMIN) {
            var userrr = user.getRole()== Role.ADMIN;
            System.out.println(userrr);
            Question savedQuestion = questionRepository.save(question);
            return ResponseEntity.ok(savedQuestion);
        } else {
            return ResponseEntity.status(403).body("Unauthorized: Only admins can create questions.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        if (user.getRole() == Role.ADMIN) {
            if (questionRepository.existsById(id)) {
                questionRepository.deleteById(id);
                return ResponseEntity.ok("Question deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(403).body("Unauthorized: Only admins can delete questions.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable UUID id, @RequestBody Question updatedQuestion,@AuthenticationPrincipal User user) {
        if(user.getRole() == Role.ADMIN){
            return questionRepository.findById(id).map(question -> {
                question.setQuestionText(updatedQuestion.getQuestionText());
//                question.setType(updatedQuestion.getType());
                question.setType(updatedQuestion.getType());
                question.setType_B(updatedQuestion.getType_B());
                question.setType_C(updatedQuestion.getType_C());
                question.setType_D(updatedQuestion.getType_D());
                question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
                questionRepository.save(question);
                return ResponseEntity.ok(question);
            }).orElse(ResponseEntity.notFound().build());
        }else {
            return ResponseEntity.status(403).body("Unauthorized: Only admins can update questions.");
        }
    }
}
