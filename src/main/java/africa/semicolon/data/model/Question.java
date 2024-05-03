package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    @Id
    private String questionId;
    private QuestionType questionType;
    private int currentQuestionNumber;
    private String questionContent;
    private List<Option> options = new ArrayList<>();
    private String answer;
    private long timeLimit;
}
