package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, UUID> {
}
