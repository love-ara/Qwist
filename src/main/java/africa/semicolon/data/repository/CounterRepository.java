package africa.semicolon.data.repository;

import africa.semicolon.data.model.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends MongoRepository<Counter, String> {

}
