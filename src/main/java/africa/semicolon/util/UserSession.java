package africa.semicolon.util;

import lombok.Data;

@Data
public class UserSession {
    private String userId;
    private String quizId;
    private int currentQuestionNumber = 0;
    private int totalScore = 0;

    public void increaseQuestionNumber() {
         currentQuestionNumber++;
     }
     public void increaseScore() {
        totalScore ++;
     }

}

