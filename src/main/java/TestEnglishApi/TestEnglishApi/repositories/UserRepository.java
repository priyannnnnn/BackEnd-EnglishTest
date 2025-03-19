package TestEnglishApi.TestEnglishApi.repositories;

import TestEnglishApi.TestEnglishApi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User>findById(UUID userId);
}
