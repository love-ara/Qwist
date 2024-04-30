package africa.semicolon.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TakeQuizResponse {
    private String quizId;
    private String quizName;
    private String userId;
    private String username;
    private List<String> userAnswers;
    private int score;
}
