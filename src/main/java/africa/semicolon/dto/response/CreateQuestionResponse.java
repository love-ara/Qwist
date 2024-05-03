package africa.semicolon.dto.response;

import africa.semicolon.dto.response.OptionResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuestionResponse {
    private String questionId;
    private int currentQuestionNumber;
    private String questionType;
    private String questionContent;
    private List<OptionResponse> option = new ArrayList<>();
    private String answer;

}
