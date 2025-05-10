package TestEnglishApi.TestEnglishApi.dtos;

import java.io.Serializable;

public class WordWithCategory implements Serializable {
    private String word;
    private String correctCategory;

    public WordWithCategory() {}

    public WordWithCategory(String word, String correctCategory) {
        this.word = word;
        this.correctCategory = correctCategory;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCorrectCategory() {
        return correctCategory;
    }

    public void setCorrectCategory(String correctCategory) {
        this.correctCategory = correctCategory;
    }
}
