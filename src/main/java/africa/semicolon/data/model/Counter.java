package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("counters")
@Data
public class Counter {
    @Id
    private String id;
    private int currentQuestionNumber;
    private int currentQuizNumber;
}
