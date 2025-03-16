package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByType(String type);
}
