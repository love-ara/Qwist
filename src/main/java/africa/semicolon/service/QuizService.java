package africa.semicolon.service;

import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.DeleteQuizRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.DeleteQuizResponse;
import africa.semicolon.dto.response.UpdateQuestionResponse;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {
    CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest);

    UpdateQuestionResponse updateQuiz(UpdateQuizRequest updateQuizRequest);

    DeleteQuizResponse deleteQuiz(DeleteQuizRequest quizId);
}
