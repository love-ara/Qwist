package africa.semicolon.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetQuizResponse {
    private String QuizTitle;
    private List<CreateQuestionResponse> getQuestionResponse = new ArrayList<>();
    private String quizPin;
}
