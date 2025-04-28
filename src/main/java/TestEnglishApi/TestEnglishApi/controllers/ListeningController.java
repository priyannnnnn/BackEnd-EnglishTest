package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Listening;
import TestEnglishApi.TestEnglishApi.entities.Role;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.service.ListeningService;
//import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auth/listening")
@Configuration
@CrossOrigin(origins = {"http://localhost:3000", "https://139.162.6.202",  "http://139.162.6.202"})
public class ListeningController {

    @Autowired
    private ListeningService service;

    private final String AUDIO_UPLOAD_DIR = "uploads/";

    @PostMapping("/create")
    public ResponseEntity<?> uploadAudioWithQuestion(
            @RequestParam("file") MultipartFile file,
            @RequestParam("typeA") String typeA,
            @RequestParam("typeB") String typeB,
            @RequestParam("typeC") String typeC,
            @RequestParam("typeD") String typeD,
            @RequestParam("correctAnswer") String correctAnswer,
            @AuthenticationPrincipal User user
            )throws IOException{
        if (user.getRole() == Role.ADMIN ){
            String filename = UUID.randomUUID()+ "_" + file.getOriginalFilename();
            Path filePath = Paths.get(AUDIO_UPLOAD_DIR + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            Listening question = new Listening();
            question.setAudioPath(filename);
            question.setTypeA(typeA);
            question.setTypeB(typeB);
            question.setTypeC(typeC);
            question.setTypeD(typeD);
            question.setCorrectAnswer(correctAnswer.toUpperCase());

            return ResponseEntity.ok(service.saveQuestion(question));
        }else {
            return ResponseEntity.status(403).body("only admin Can Create Questions");
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Listening>> getAllForUsers() {
        return ResponseEntity.ok(service.getAllQuestions());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateListening(
            @PathVariable UUID id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("typeA") String typeA,
            @RequestParam("typeB") String typeB,
            @RequestParam("typeC") String typeC,
            @RequestParam("typeD") String typeD,
            @RequestParam("correctAnswer") String correctAnswer,
            @AuthenticationPrincipal User user
    ) throws IOException{
        Listening existing = service.getById(id);
        if (existing == null ){
            return ResponseEntity.notFound().build();
        }
        if(file != null && user.getRole() ==Role.ADMIN){
            String filename = UUID.randomUUID()+"_"+ file.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            existing.setAudioPath(filename);
        }
        existing.setTypeA(typeA);
        existing.setTypeB(typeB);
        existing.setTypeC(typeC);
        existing.setTypeD(typeD);
        existing.setCorrectAnswer(correctAnswer.toUpperCase());

        return ResponseEntity.ok(service.saveQuestion(existing));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteListening(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        Listening existing = service.getById(id);
        if (user.getRole() == Role.ADMIN){
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            service.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        }else {
             return ResponseEntity.status(403).body("ony Admin can Delete");
        }
    }

    @GetMapping("/audio/{filename:.+}")
    public ResponseEntity<Resource> getAudio(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get("uploads").resolve(filename).normalize();

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(resource);
    }
}
