package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.dtos.SubmitAnswerRequest;
import TestEnglishApi.TestEnglishApi.dtos.VocabularySubmitRequest;
import TestEnglishApi.TestEnglishApi.entities.Score;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.entities.Vocabulary;
import TestEnglishApi.TestEnglishApi.entities.VocabularyWord;
import TestEnglishApi.TestEnglishApi.repositories.ScoreRepository;
import TestEnglishApi.TestEnglishApi.repositories.UserRepository;
import TestEnglishApi.TestEnglishApi.repositories.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VocabularyService {

    @Autowired
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ScoreRepository scoreRepository;

    public VocabularyService(VocabularyRepository vocabularyRepository, UserRepository userRepository, ScoreRepository scoreRepository) {
        this.vocabularyRepository = vocabularyRepository;
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
    }

    public Vocabulary createQuestion(Vocabulary vocabulary){
        for(VocabularyWord word : vocabulary.getWords()){
            word.setQuestion(vocabulary);
        }
        return vocabularyRepository.save(vocabulary);
    }

    public List<Vocabulary> getAllQuestions() {
        return vocabularyRepository.findAll();
    }

    public Map<String, Object> submitAnswers(UUID questionId, SubmitAnswerRequest request){
        Optional<Vocabulary> vocabOpt = vocabularyRepository.findById(questionId);
        if (vocabOpt.isEmpty()) {
            throw new RuntimeException("Question not found");
        }

        Vocabulary vocab = vocabOpt.get();
        Map<String, String> wordToCategory = new HashMap<>();
        for (VocabularyWord word : vocab.getWords()) {
            wordToCategory.put(word.getWord(), word.getCorrectCategory());
        }

        int total = 0;
        int correct = 0;

        for (SubmitAnswerRequest.AnswerGroup group : request.getAnswers()) {
            for (String submittedWord : group.getSubmittedWords()) {
                total++;
                String correctCategory = wordToCategory.get(submittedWord);
                if (correctCategory != null && correctCategory.equalsIgnoreCase(group.getSelectedCategory())) {
                    correct++;
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("correct", correct);
        result.put("score", total > 0 ? (double) correct / total * 100 : 0.0);
        return result;
    }

    public int SubmitAnswersAndcalculation(VocabularySubmitRequest request){
        int totalScore = 0;

        for (VocabularySubmitRequest.AnswerItem item : request.getAnswers()){
            Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(item.getQuestionId());
            if (optionalVocabulary.isEmpty()) continue;

            Vocabulary question =  optionalVocabulary.get();
            Map<String, String> correctMap = question.getWords()
                    .stream().collect(Collectors.toMap(VocabularyWord::getWord, VocabularyWord::getCorrectCategory));

            for (VocabularySubmitRequest.AnswerItem.SubmittedCategory submitted : item.getSubmittedCategories()){
                for (String word : submitted.getSubmittedWords()){
                    if (correctMap.containsKey(word) && correctMap.get(word).equalsIgnoreCase(submitted.getSelectedCategory())){
                        totalScore ++;
                    }
                }
            }

        }

        User user = userRepository.findById(request.getUserId()).orElseThrow();
        Score score = new Score();
        score.setUser(user);
        score.setTotalScore(totalScore);
        score.setDateTaken(LocalDateTime.now());
        score.setSectionType("vocabulary");
        scoreRepository.save(score);

        return totalScore;
    }

}
