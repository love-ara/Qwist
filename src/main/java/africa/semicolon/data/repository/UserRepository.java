package africa.semicolon.data.repository;

import africa.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    User findByUsername(String username);

    User findByEmail(String input);
}
