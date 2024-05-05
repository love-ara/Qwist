package africa.semicolon.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class QuizResultResponse {
    private String message;
    private int finalScore;
    private List<String> feedbackList;


}
