package africa.semicolon.service.services;

import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.DeleteQuizRequest;
import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.*;
import org.springframework.stereotype.Service;


@Service
public interface QuizService {
    CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest);

    UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest);

    DeleteQuizResponse deleteQuiz(DeleteQuizRequest quizId);

    ViewQuizResponse viewQuiz(String quizPin);
    GetQuizResponse getQuiz(GetQuizRequest getQuizRequest);
}
