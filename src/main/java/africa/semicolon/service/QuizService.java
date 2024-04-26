package africa.semicolon.service;

import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.UpdateQuizResponse;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {
    CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest);

    UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest);
}
