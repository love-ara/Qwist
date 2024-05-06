package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class UpdateQuizRequest {
    private String quizId;
    private String quizTitle;
    private String quizDescription;
    private UpdateQuestionRequest updateQuestionRequest = new UpdateQuestionRequest();
    private String username;
}
