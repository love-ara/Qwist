package africa.semicolon.data.repository;

import africa.semicolon.data.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String > {
    boolean existsByQuestionContent(String QuestionContent);

}
