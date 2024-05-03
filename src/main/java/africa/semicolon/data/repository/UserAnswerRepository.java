package africa.semicolon.data.repository;

import africa.semicolon.data.model.UserAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends MongoRepository <UserAnswer, String>{
}
