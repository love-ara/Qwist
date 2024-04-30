package africa.semicolon.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TakeQuizRequest {
    private String quizId;
    private String quizName;
    private String userId;
    private String username;
    private List<String> answer;
}
