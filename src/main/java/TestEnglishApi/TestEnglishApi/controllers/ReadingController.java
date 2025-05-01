package TestEnglishApi.TestEnglishApi.controllers;

import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.entities.Reading;
import TestEnglishApi.TestEnglishApi.entities.Role;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/reading")
@Configuration
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173",  "http://139.162.6.202"})
public class ReadingController {

    @Autowired
    private ReadingService service;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal User user, @RequestBody Reading question){
        if (user.getRole() == Role.ADMIN){
            Reading saveReading = service.save(question);
            return ResponseEntity.ok(saveReading);
        }else {
            return ResponseEntity.status(403).body("only admin acces");
        }
    }

    @GetMapping
    public List<Reading>getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reading getById(@PathVariable UUID id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        if (user.getRole() == Role.ADMIN) {
            if (service.existsById(id)) {
                service.deleteById(id); // ✅ actually perform the deletion
                return ResponseEntity.ok("Reading question deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(403).body("Unauthorized: Only admins can delete reading questions.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @RequestBody Reading updateReading){

        if (user.getRole() != Role.ADMIN){
            return ResponseEntity.status(403).body("only Admin Acces");
        }
        Reading updated = service.updateReading(id, updateReading);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Reading question not found.");
        }
    }



}
