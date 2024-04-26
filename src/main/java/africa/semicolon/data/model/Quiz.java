package africa.semicolon.data.model;

import africa.semicolon.dto.response.CreateQuestionResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class Quiz {
    @Id
    private String quizId;
    private String quizName;
    private String quizDescription;
    private List<Question> questions = new ArrayList<>();
}
