package africa.semicolon.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ViewQuizResponse {
    private String QuizTitle;
    private List<ViewQuizQuestionResponse> viewQuizQuestionResponses = new ArrayList<>();
    private String quizPin;
}
