package africa.semicolon.dto.response;

import africa.semicolon.dto.request.OptionRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateQuizResponse {
    private String updateQuizId;
    private String updatedQuizDescription;
    private String updatedQuizName;

}
