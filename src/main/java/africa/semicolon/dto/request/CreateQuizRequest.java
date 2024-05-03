package africa.semicolon.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuizRequest {
   private String quizTitle;
   private List<CreateQuestionRequest> createQuestionRequest = new ArrayList<>();
}
