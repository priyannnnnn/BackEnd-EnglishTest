package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {
}
