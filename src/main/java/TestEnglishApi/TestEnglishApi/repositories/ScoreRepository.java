package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    Optional<Score>findByUserIdAndSectionType(UUID userId, String sectionType);
    List<Score> findByUserId(UUID userId);
}
