package africa.semicolon.dto.response;

import africa.semicolon.data.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuizResponse {
    private String quizId;
    private int quizNumber;
    private String quizName;
    private String quizDescription;
    private List<CreateQuestionResponse> createQuestionResponse = new ArrayList<>();

}
