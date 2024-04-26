package africa.semicolon.service;

import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.UpdateQuestionRequest;
import africa.semicolon.dto.response.UpdateQuestionResponse;
import africa.semicolon.dto.response.CreateQuestionResponse;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    CreateQuestionResponse createQuestion(CreateQuestionRequest createQuestionRequest);

    UpdateQuestionResponse updateQuestion(UpdateQuestionRequest updateQuestionRequest);
}
