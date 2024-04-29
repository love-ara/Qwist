package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("QuestionRepo")
public class Question {
    @Id
    private String questionId;
    private String questionContent;
    private List<Option> options = new ArrayList<>();
    private String correctAnswer;
}
