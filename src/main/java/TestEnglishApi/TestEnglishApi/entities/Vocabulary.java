package TestEnglishApi.TestEnglishApi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vocabulary_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(name = "categories", columnDefinition = "text[]")
    private String[] categories;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VocabularyWord> words = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public List<VocabularyWord> getWords() {
        return words;
    }

    public void setWords(List<VocabularyWord> words) {
        this.words = words;
    }
}
