package africa.semicolon.dto.response;

import lombok.Data;

@Data
public class CreateQuizResponse {
    private String quizId;
    private String quizName;
    private String quizDescription;
    private CreateQuestionResponse createQuestionResponse;
}
