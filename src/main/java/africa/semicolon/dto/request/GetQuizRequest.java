package africa.semicolon.dto.request;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class GetQuizRequest {
    private String quizPin;
    private String username;
    private Map<String, String> userAnswers = new HashMap<>();


}
