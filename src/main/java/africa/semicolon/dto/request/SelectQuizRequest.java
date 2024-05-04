package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class SelectQuizRequest {
    private String quizId;
    private String quizTitle;
    private String quizCategory;
    private String username;
}
