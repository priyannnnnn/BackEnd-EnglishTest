package TestEnglishApi.TestEnglishApi.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SubmitAnswerRequest {
    private List<AnswerGroup> answers;

    @Data
    public static class AnswerGroup {
        private List<String> submittedWords;
        private String selectedCategory;
    }

}
