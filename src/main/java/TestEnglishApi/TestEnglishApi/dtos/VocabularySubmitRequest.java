package TestEnglishApi.TestEnglishApi.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VocabularySubmitRequest {
    private List<AnswerItem> answers;
    private UUID userId;

    @Data
    public static class AnswerItem {
        private UUID questionId;
        private List<SubmittedCategory> submittedCategories;

        @Data
        public static class SubmittedCategory {
            private String selectedCategory;
            private List<String> submittedWords;
        }
    }
}
