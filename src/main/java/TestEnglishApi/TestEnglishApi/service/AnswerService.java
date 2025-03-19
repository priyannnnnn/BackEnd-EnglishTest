package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.dtos.AnswerDTO;
import TestEnglishApi.TestEnglishApi.entities.Answer;
import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.entities.Score;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.repositories.AnswerRepository;
import TestEnglishApi.TestEnglishApi.repositories.QuestionRepository;
import TestEnglishApi.TestEnglishApi.repositories.ScoreRepository;
import TestEnglishApi.TestEnglishApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final ScoreRepository scoreRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public List<Answer>submitAnswers(UUID userId, List<AnswerDTO> answers){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Usser Not found"));

        List<Answer>savedAnswers = new ArrayList<>();
        int totalScore = 0;

        for (AnswerDTO answerDTO : answers){
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(()-> new RuntimeException("Question not found"));

            boolean isCorrect = question.getCorrectAnswer().equals(answerDTO.getSelectedAnswer());

            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setSelectedAnswer(answerDTO.getSelectedAnswer());
            answer.setCorrect(isCorrect);

            if (isCorrect){
                totalScore += 10;
            }

            savedAnswers.add(answerRepository.save(answer));
        }

        Score score = scoreRepository.findByUserId(userId).orElse(new Score());
        score.setUser(user);
        score.setTotalScore(totalScore);
        score.setDateTaken(LocalDateTime.now());
        scoreRepository.save(score);

        return savedAnswers;
    }
}
