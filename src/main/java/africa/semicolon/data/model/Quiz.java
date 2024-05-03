package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
public class Quiz {
    @Id
    private String quizId;
    private int quizNumber;
    private String quizTitle;
    @DBRef
    private List<Question> questions = new ArrayList<>();
}
