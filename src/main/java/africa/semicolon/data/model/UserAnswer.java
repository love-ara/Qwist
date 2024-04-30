package africa.semicolon.data.model;

import lombok.Data;

@Data
public class UserAnswer {
    private String answerId;
    private String questionId;
    private String userAnswer;
    private boolean isCorrect;
    private String userId;
}
