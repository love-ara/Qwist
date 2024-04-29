package africa.semicolon.service;

import africa.semicolon.data.model.Option;
import africa.semicolon.data.model.Question;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.DeleteQuestionRequest;
import africa.semicolon.dto.request.OptionRequest;
import africa.semicolon.dto.request.UpdateQuestionRequest;
import africa.semicolon.dto.response.CreateQuestionResponse;
import africa.semicolon.dto.response.DeleteQuestionResponse;
import africa.semicolon.dto.response.UpdateQuestionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImplementation implements QuestionService{
    private QuestionRepository questionRepository;

    @Override
    public CreateQuestionResponse createQuestion(CreateQuestionRequest createQuestionRequest) {
        if (questionRepository.existsByQuestionContent(createQuestionRequest.getQuestionContent())) {
            throw new IllegalArgumentException("Question already exists");
        }
        Question question = mapQuestion(createQuestionRequest);
       var savedQuestion = questionRepository.save(question);

        return mapQuestionResponse(savedQuestion);
    }

    @Override
    public UpdateQuestionResponse updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        Question oldQuestion = findQuestionBy(updateQuestionRequest.getQuestionId());
        Question question = new Question();
        updateQuestionMap(updateQuestionRequest, question);
        questionRepository.delete(oldQuestion);
        var updatedQuestion = questionRepository.save(question);

        return updateQuestionResponseMap(updateQuestionRequest, updatedQuestion);
    }

    @Override
    public DeleteQuestionResponse deleteQuestion(DeleteQuestionRequest deleteQuestionRequest) {
        Question question = findQuestionBy(deleteQuestionRequest.getQuestionId());

        DeleteQuestionResponse deleteQuestionResponse = new DeleteQuestionResponse();
        deleteQuestionResponse.setQuestionId(question.getQuestionId());
        questionRepository.delete(question);

        return deleteQuestionResponse;
    }

    private static void updateQuestionMap(UpdateQuestionRequest updateQuestionRequest, Question question) {
        question.setQuestionId(updateQuestionRequest.getQuestionId());
        updateQuestMap(updateQuestionRequest, question);
    }
    private static void updateQuestMap(UpdateQuestionRequest updateQuestionRequest, Question question) {
        question.setQuestionContent(updateQuestionRequest.getQuestionContent());
        List<Option> options = new ArrayList<>();
        for(OptionRequest optionRequest : updateQuestionRequest.getOptions()) {
            Option option = new Option();
            option.setOptionContent(optionRequest.getOptionContent());
            options.add(option);
        }
        question.setOptions(options);
        question.setCorrectAnswer(updateQuestionRequest.getAnswer());
    }

    private static UpdateQuestionResponse updateQuestionResponseMap(UpdateQuestionRequest updateQuestionRequest, Question updatedQuestion) {
        UpdateQuestionResponse response = new UpdateQuestionResponse();
        response.setQuestionId(updatedQuestion.getQuestionId());
        response.setQuestionContent(updatedQuestion.getQuestionContent());
        response.setOptions(updateQuestionRequest.getOptions());
        response.setAnswer(updateQuestionRequest.getAnswer());
        return response;
    }

    private Question findQuestionBy(String  id) {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    private static CreateQuestionResponse mapQuestionResponse(Question question) {
        CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
        createQuestionResponse.setQuestionId(question.getQuestionId());
        return createQuestionResponse;
    }

    private static Question mapQuestion(CreateQuestionRequest createQuestionRequest) {
        Question question = new Question();
        question.setQuestionContent(createQuestionRequest.getQuestionContent());

        List<Option> options = new ArrayList<>();
        for (int index = 0; index < createQuestionRequest.getOption().size(); index++){
            Option option = new Option();
            option.setOptionContent(createQuestionRequest.getOption().get(index).getOptionContent());
            options.add(option);
        }
        question.setOptions(options);
        question.setCorrectAnswer(createQuestionRequest.getAnswer());

        return question;
    }
}
