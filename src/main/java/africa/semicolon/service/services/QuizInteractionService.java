package africa.semicolon.service.services;



import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.response.QuizResultResponse;
import africa.semicolon.dto.response.ViewQuizResponse;
import org.springframework.stereotype.Service;


@Service
public interface QuizInteractionService{
    ViewQuizResponse displayQuiz(String  quizPin);
    QuizResultResponse collectUserAnswersAndCalculateScore(String quizPin, GetQuizRequest getQuizRequest);
}
