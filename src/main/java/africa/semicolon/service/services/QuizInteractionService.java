package africa.semicolon.service.services;



import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.response.GetQuizResponse;
import africa.semicolon.dto.response.QuizResultResponse;
import org.springframework.stereotype.Service;


@Service
public interface QuizInteractionService{
    GetQuizResponse displayQuiz(String  quizPin);
    QuizResultResponse collectUserAnswersAndCalculateScore(String quizPin, GetQuizRequest getQuizRequest);
}
