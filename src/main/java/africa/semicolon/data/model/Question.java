package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Question {
    @Id
    private String questionId;
    private String questionContent;
    private List<Option> options;
    private String correctAnswer;
}
