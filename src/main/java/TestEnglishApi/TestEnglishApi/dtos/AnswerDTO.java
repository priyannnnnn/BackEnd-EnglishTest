package TestEnglishApi.TestEnglishApi.dtos;

import java.util.UUID;

public class AnswerDTO {
    private UUID questionId;
    private String SelectedAnswer;

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return SelectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        SelectedAnswer = selectedAnswer;
    }
}
