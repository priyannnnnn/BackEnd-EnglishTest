package TestEnglishApi.TestEnglishApi.entities;

import TestEnglishApi.TestEnglishApi.dtos.WordWithCategory;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vocabulary_question")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @ElementCollection
    private List<String> categories;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private List<WordWithCategory> words;

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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<WordWithCategory> getWords() {
        return words;
    }

    public void setWords(List<WordWithCategory> words) {
        this.words = words;
    }
}
