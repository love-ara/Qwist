package africa.semicolon.dto.response;

import lombok.Data;

@Data
public class SelectQuizResponse {
    private String quizId;
    private String quizTitle;
    private String quizDescription;
    private String question;
}
