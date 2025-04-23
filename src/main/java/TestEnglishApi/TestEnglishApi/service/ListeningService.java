package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.entities.Listening;
import TestEnglishApi.TestEnglishApi.repositories.ListeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListeningService {

    @Autowired
    private ListeningRepository repository;

    public Listening saveQuestion(Listening question){
        return repository.save(question);
    }

    public List<Listening> getAllQuestions(){
        return repository.findAll().stream().map(q-> {
            Listening clone = new Listening();
            clone.setId(q.getId());
            clone.setAudioPath(q.getAudioPath());
            clone.setTypeA(q.getTypeA());
            clone.setTypeB(q.getTypeB());
            clone.setTypeC(q.getTypeC());
            clone.setTypeD(q.getTypeD());
            clone.setCorrectAnswer(q.getCorrectAnswer());
            return clone;
        }).collect(Collectors.toList());
    }

    public Listening getById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public void  deleteById(UUID id){
        repository.deleteById(id);
    }
}
