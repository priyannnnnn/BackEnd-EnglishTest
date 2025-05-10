package TestEnglishApi.TestEnglishApi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "vocabulary_word")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyWord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String word;
    private String correctCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Vocabulary question;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Vocabulary getQuestion() {
        return question;
    }

    public void setQuestion(Vocabulary question) {
        this.question = question;
    }
}
