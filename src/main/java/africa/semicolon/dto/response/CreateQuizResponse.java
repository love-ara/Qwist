package africa.semicolon.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuizResponse {
    private String quizId;
    private String quizTitle;
    private List<CreateQuestionResponse> createQuestionResponse = new ArrayList<>();
    private String quizPin;
    private String username;
}
