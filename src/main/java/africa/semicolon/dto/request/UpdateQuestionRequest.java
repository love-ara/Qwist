package africa.semicolon.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateQuestionRequest {
    private String questionId;
    private int currentQuestionNumber;
    private String questionContent;
    private List<OptionRequest> options = new ArrayList<>();
    private String answer;
}
