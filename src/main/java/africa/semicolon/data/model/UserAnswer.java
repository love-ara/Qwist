package africa.semicolon.data.model;

import lombok.Data;

@Data
public class UserAnswer {
    private String answerId;
    private String questionId;
    private Question question;
    private String userAnswer;
    private boolean isCorrect;
    private String userId;
    private long timeTaken;
}
