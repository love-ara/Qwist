package africa.semicolon.service.services;

import africa.semicolon.data.model.Question;
import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.DeleteQuizRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.DeleteQuizResponse;
import africa.semicolon.dto.response.UpdateQuizResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest);

    UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest);

    DeleteQuizResponse deleteQuiz(DeleteQuizRequest quizId);

    List<Question> getQuizQuestions(String quizId);
}
