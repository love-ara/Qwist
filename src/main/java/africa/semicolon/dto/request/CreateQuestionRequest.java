package africa.semicolon.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CreateQuestionRequest {
    private String questionContent;
    private String questionType;
    private List<OptionRequest> option = new ArrayList<>();
    private String answer;

}
