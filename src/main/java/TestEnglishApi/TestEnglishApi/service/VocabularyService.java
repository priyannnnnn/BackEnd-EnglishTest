package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.entities.Vocabulary;
import TestEnglishApi.TestEnglishApi.repositories.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VocabularyService {

    @Autowired
    private final VocabularyRepository vocabularyRepository;

    public VocabularyService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public Vocabulary create(Vocabulary item){
        return vocabularyRepository.save(item);
    }
    public List<Vocabulary> getAllQuestions(){
        return vocabularyRepository.findAll();
    }

    public Vocabulary getQuestionById(UUID id){
        return vocabularyRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public void delete(UUID id){
        vocabularyRepository.deleteById(id);
    }
}
