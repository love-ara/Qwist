package africa.semicolon.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuestionRequest {
    private String questionId;
    private String questionContent;
    private List<OptionRequest> options;
    private String answer;
}
