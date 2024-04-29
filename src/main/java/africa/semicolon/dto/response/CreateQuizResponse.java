package africa.semicolon.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuizResponse {
    private String quizId;
    private String quizName;
    private String quizDescription;
    private List<CreateQuestionResponse> createQuestionResponse = new ArrayList<>();
}
