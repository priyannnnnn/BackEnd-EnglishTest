package TestEnglishApi.TestEnglishApi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false, name = "type_a")
    private String type;

//    @Column(nullable = false, name = "type_a")
//    private String type_A;

    @Column(nullable = false, name = "type_b")
    private String type_B;

    @Column(nullable = false, name = "type_c")
    private String type_C;

    @Column(nullable = false, name = "type_d")
    private String type_D;

    @Column(nullable = false)
    private String correctAnswer;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

//    public String getType_A() {
//        return type_A;
//    }
//
//    public void setType_A(String type_A) {
//        this.type_A = type_A;
//    }

    public String getType_B() {
        return type_B;
    }

    public void setType_B(String type_B) {
        this.type_B = type_B;
    }

    public String getType_C() {
        return type_C;
    }

    public void setType_C(String type_C) {
        this.type_C = type_C;
    }

    public String getType_D() {
        return type_D;
    }

    public void setType_D(String type_d) {
        this.type_D = type_d;
    }
}
