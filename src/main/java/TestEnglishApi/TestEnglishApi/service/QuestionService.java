package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    public List<Question>getAllQuestions(){
        return questionRepository.findAll();
    }

    public List<Question>getReadingQuestions(){
        return questionRepository.findByType("reading");
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question getById(UUID id){
        return questionRepository.findById(id).orElseThrow(()-> new RuntimeException("Question not found with id: " + id));
    }

    public Question updateQuestion(UUID id, Question newQuestion) {
        return questionRepository.findById(id).map(q -> {
            q.setQuestionText(newQuestion.getQuestionText());
//            q.setType(newQuestion.getType());
            q.setType(newQuestion.getType());
            q.setType_B(newQuestion.getType_B());
            q.setType_C(newQuestion.getType_C());
            q.setType_D(newQuestion.getType_D());
            q.setCorrectAnswer(newQuestion.getCorrectAnswer());
            return questionRepository.save(q);
        }).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Transactional
    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
        questionRepository.flush();
    }

}
