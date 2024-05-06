package africa.semicolon.dto.response;

import lombok.Data;

@Data
public class UpdateQuizResponse {
    private String updateQuizId;
    private String updatedQuizDescription;
    private String updatedQuizTitle;
    private String username;
}
