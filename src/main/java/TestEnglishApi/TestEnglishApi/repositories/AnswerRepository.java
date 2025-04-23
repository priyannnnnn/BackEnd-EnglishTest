package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.Answer;
import TestEnglishApi.TestEnglishApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    // Find answers by user
    List<Answer> findByUser(User user);

    // Find answers only for users with the ADMIN role
    @Query("SELECT a FROM Answer a WHERE a.user.role = 'ADMIN'")
    List<Answer> findByAdminUsers();
}

