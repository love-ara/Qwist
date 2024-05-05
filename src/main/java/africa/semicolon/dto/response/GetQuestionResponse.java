package africa.semicolon.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetQuestionResponse {
    private int currentQuestionNumber;
    private String questionContent;
    private List<OptionResponse> options;

}
