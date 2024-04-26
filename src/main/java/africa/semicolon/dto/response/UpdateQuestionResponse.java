package africa.semicolon.dto.response;

import africa.semicolon.dto.request.OptionRequest;
import lombok.Data;

import java.awt.desktop.OpenFilesEvent;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateQuestionResponse {
    private String questionId;
    private String questionContent;
    private List<OptionRequest> options = new ArrayList<>();
    private String answer;

}
