package TestEnglishApi.TestEnglishApi.service;

import TestEnglishApi.TestEnglishApi.dtos.AnswerDTO;
import TestEnglishApi.TestEnglishApi.entities.Answer;
import TestEnglishApi.TestEnglishApi.entities.Question;
import TestEnglishApi.TestEnglishApi.entities.Score;
import TestEnglishApi.TestEnglishApi.entities.User;
import TestEnglishApi.TestEnglishApi.repositories.*;
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
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ListeningRepository listeningRepository;
    private final ReadingRepository readingRepository;

    public List<Answer> submitAnswers(UUID userId, List<AnswerDTO> answers) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usser Not found"));
        List<Answer> savedAnswers = new ArrayList<>();
        int totalScore = 0;

        for (AnswerDTO dto : answers) {
            String section = dto.getSectionType().toUpperCase();
            String correctAnswer = "";

            switch (section) {
                case "GRAMMAR":
                    correctAnswer = questionRepository.findById(dto.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("Grammar Questions not found"))
                            .getCorrectAnswer();
                    break;
                case "LISTENING":
                    correctAnswer = listeningRepository.findById(dto.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("Listening not found"))
                            .getCorrectAnswer();
                    break;
                case "READING":
                    correctAnswer = readingRepository.findById(dto.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("Reading not found"))
                            .getCorrectAnswer();
                    break;
                default:
                    throw new RuntimeException("Invalid section type: " + section);
            }
            boolean isCorrect = correctAnswer.equalsIgnoreCase(dto.getSelectedAnswer());
            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestionId(dto.getQuestionId());
            answer.setSelectedAnswer(dto.getSelectedAnswer());
            answer.setCorrect(isCorrect);
            answer.setSectionType(section);

            savedAnswers.add(answerRepository.save(answer));
            if (isCorrect) totalScore += 10;
        }

        Score score = scoreRepository.findByUserIdAndSectionType(userId, answers.get(0).getSectionType().toUpperCase())
                .orElse(new Score());
        score.setUser(user);
        score.setSectionType(answers.get(0).getSectionType().toUpperCase());
        score.setTotalScore(totalScore);
        score.setDateTaken(LocalDateTime.now());

        scoreRepository.save(score);
        return savedAnswers;
    }
}

//            Answer answer = new Answer();
//            answer.setUser(user);
//            answer.setQuestion(question);
//            answer.setSelectedAnswer(answerDTO.getSelectedAnswer());
//            answer.setCorrect(isCorrect);
//            answer.setSectionType(answerDTO.getSectionType()); // NEW
//
//            sectionType = answerDTO.getSectionType(); // store for Score later
//
//            if (isCorrect){
//                totalScore += 10;
//            }
//
//            savedAnswers.add(answerRepository.save(answer));
//        }
//
//        Score score = scoreRepository.findByUserId(userId).orElse(new Score());
//        score.setUser(user);
//        score.setTotalScore(totalScore);
//        score.setDateTaken(LocalDateTime.now());
//        score.setSectionType(sectionType); // NEW
//        scoreRepository.save(score);
//
//        return savedAnswers;

