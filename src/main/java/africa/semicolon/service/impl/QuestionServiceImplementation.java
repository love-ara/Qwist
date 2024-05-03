package africa.semicolon.service.impl;

import africa.semicolon.data.model.Counter;
import africa.semicolon.data.model.Option;
import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.QuestionType;
import africa.semicolon.data.repository.CounterRepository;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.DeleteQuestionRequest;
import africa.semicolon.dto.request.OptionRequest;
import africa.semicolon.dto.request.UpdateQuestionRequest;
import africa.semicolon.dto.response.CreateQuestionResponse;
import africa.semicolon.dto.response.DeleteQuestionResponse;
import africa.semicolon.dto.response.OptionResponse;
import africa.semicolon.dto.response.UpdateQuestionResponse;
import africa.semicolon.service.services.QuestionService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImplementation implements QuestionService {
    private QuestionRepository questionRepository;
    private CounterRepository counterRepository;

    @Override
    public CreateQuestionResponse createQuestion(CreateQuestionRequest createQuestionRequest) {
        if (questionRepository.existsByQuestionContent(createQuestionRequest.getQuestionContent())) {
            throw new IllegalArgumentException("Question already exists");
        }
        int currentQuestionNumber = getNextQuestionNumber();
        Question question = mapQuestion(createQuestionRequest, currentQuestionNumber);
       var savedQuestion = questionRepository.save(question);

        return mapQuestionResponse(savedQuestion);
    }

    @PostConstruct
    public void initializeCounter() {
        counterRepository.findById("questionCounter").orElseGet(() -> {
            Counter counter = new Counter();
            counter.setId("questionCounter");
            counter.setCurrentQuestionNumber(0);
            return counterRepository.save(counter);
        });
    }


    public int getNextQuestionNumber() {
        Counter counter = counterRepository.findById("questionCounter").orElseGet(Counter::new);

        int currentQuestionNumber = counter.getCurrentQuestionNumber() + 1;
        counter.setCurrentQuestionNumber(currentQuestionNumber);

        counterRepository.save(counter);

        return currentQuestionNumber;
    }


    @Override
    public UpdateQuestionResponse updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        Question oldQuestion = findQuestionBy(updateQuestionRequest.getQuestionId());
        updateQuestMap(updateQuestionRequest, oldQuestion);
        var updatedQuestion = questionRepository.save(oldQuestion);

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


    private static void updateQuestMap(UpdateQuestionRequest updateQuestionRequest, Question question) {
        question.setQuestionId(updateQuestionRequest.getQuestionId());

        int originalCurrentQuestionNumber = question.getCurrentQuestionNumber();
        question.setCurrentQuestionNumber(originalCurrentQuestionNumber);

        question.setQuestionContent(updateQuestionRequest.getQuestionContent());
        List<Option> options = new ArrayList<>();
        for(OptionRequest optionRequest : updateQuestionRequest.getOptions()) {
            Option option = new Option();
            option.setOptionContent(optionRequest.getOptionContent());
            options.add(option);
        }
        question.setOptions(options);
        question.setAnswer(updateQuestionRequest.getAnswer());
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
        createQuestionResponse.setCurrentQuestionNumber(question.getCurrentQuestionNumber());
        createQuestionResponse.setQuestionType(String.valueOf(question.getQuestionType()));
        createQuestionResponse.setQuestionContent(question.getQuestionContent());
        List<OptionResponse> options = new ArrayList<>();
        optionResponse(question, options);
        createQuestionResponse.setOption(options);
        createQuestionResponse.setAnswer(question.getAnswer());
        return createQuestionResponse;
    }

    private static void optionResponse(Question question, List<OptionResponse> options) {
        for (Option option : question.getOptions()){
            OptionResponse optionResponse = new OptionResponse();
            optionResponse.setOptionContent(option.getOptionContent());
            options.add(optionResponse);
        }
    }

    private static Question mapQuestion(CreateQuestionRequest createQuestionRequest, int currentQuestionNumber ) {
        Question question = new Question();
        question.setQuestionType(QuestionType.valueOf(createQuestionRequest.getQuestionType()));
        question.setQuestionContent(createQuestionRequest.getQuestionContent());
        question.setCurrentQuestionNumber(currentQuestionNumber);
        List<Option> options = new ArrayList<>();
        for (OptionRequest optionRequest : createQuestionRequest.getOption()){
            Option option = new Option();
            option.setOptionContent(optionRequest.getOptionContent());
            options.add(option);
        }
        question.setOptions(options);
        question.setAnswer(createQuestionRequest.getAnswer());

        return question;
    }
}
