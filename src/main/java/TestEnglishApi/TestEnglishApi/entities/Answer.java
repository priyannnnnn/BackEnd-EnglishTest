package TestEnglishApi.TestEnglishApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "question_id", nullable = false)
//    private Question question;
    @Column(name = "question_id")
    private UUID questionId; // Can be from grammar, listening, or reading

    @Column(name = "section_type") // GRAMMAR, LISTENING, READING
    private String sectionType;

    @Column(name = "selected_answer",nullable = false)
    private String selectedAnswer;

    @Column( nullable = false)
    private boolean isCorrect;

}
