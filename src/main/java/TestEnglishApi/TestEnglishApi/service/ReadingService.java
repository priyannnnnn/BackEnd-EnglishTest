package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.entities.Reading;
import TestEnglishApi.TestEnglishApi.repositories.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReadingService {

    @Autowired
    private ReadingRepository repository;

    public Reading save(Reading question){
        return repository.save(question);
    }

    public List<Reading> getAll(){
        return repository.findAll();
    }

    public Reading getById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Reading updateReading(UUID id, Reading updateReading){
        return repository.findById(id).map(reading -> {
            reading.setParagraph(updateReading.getParagraph());
            reading.setQuestion(updateReading.getQuestion());
            reading.setOptionA(updateReading.getOptionA());
            reading.setOptionB(updateReading.getOptionB());
            reading.setOptionC(updateReading.getOptionC());
            reading.setOptionD(updateReading.getOptionD());
            reading.setCorrectAnswer(updateReading.getCorrectAnswer());
            return repository.save(reading);
        }).orElseThrow(() -> new RuntimeException("Question not found"));
    }
}
