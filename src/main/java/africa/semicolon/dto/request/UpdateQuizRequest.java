package africa.semicolon.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateQuizRequest {
    private String quizId;
    private String quizName;
    private String quizDescription;
    private UpdateQuestionRequest updateQuestionRequest = new UpdateQuestionRequest();
}
