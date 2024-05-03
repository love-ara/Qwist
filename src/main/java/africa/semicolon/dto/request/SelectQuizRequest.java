package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class SelectQuizRequest {
    private String quizId;
    private String quizName;
    private String quizCategory;
    private String username;
}
