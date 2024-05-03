package africa.semicolon.service.impl;

import africa.semicolon.data.model.Counter;
import africa.semicolon.data.model.Option;
import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.Quiz;
import africa.semicolon.data.repository.CounterRepository;
import africa.semicolon.data.repository.QuizRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;


import africa.semicolon.service.services.QuestionService;
import africa.semicolon.service.services.QuizService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImplementation implements QuizService {
    private QuizRepository quizRepository;
    private CounterRepository counterRepository;
    private QuestionService questionService;


    @Override
    public CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest) {
        if(quizRepository.existsByQuizTitle(createQuizRequest.getQuizTitle())){
            throw new IllegalArgumentException("A quiz with this name already exists");
        }
        int currentQuizNumber = getNextQuizNumber();
        resetQuestionCounter();


        Quiz quiz = new Quiz();

        quiz.setQuizTitle(createQuizRequest.getQuizTitle());
        quiz.setQuizNumber(currentQuizNumber);
        List<Question> questions = new ArrayList<>();
        List<CreateQuestionResponse> questionResponses = new ArrayList<>();
        for(CreateQuestionRequest createQuestionRequest : createQuizRequest.getCreateQuestionRequest()){
            Question quizQuestion = createQuestion(createQuestionRequest);
            var response = questionResponse(quizQuestion);

            questions.add(quizQuestion);
            questionResponses.add(response);
        }


        quiz.setQuestions(questions);
        var savedQuiz = quizRepository.save(quiz);

        CreateQuizResponse createQuizResponse = new CreateQuizResponse();
        createQuizResponse.setQuizId(savedQuiz.getQuizId());
        createQuizResponse.setQuizNumber(currentQuizNumber);
        createQuizResponse.setQuizName(savedQuiz.getQuizTitle());
        createQuizResponse.setCreateQuestionResponse(questionResponses);


            return createQuizResponse;
    }

    private Question createQuestion(CreateQuestionRequest createQuestionRequest) {
        var question = questionService.createQuestion(createQuestionRequest);
        Question quizQuestion = new Question();
        quizQuestion.setQuestionId(question.getQuestionId());
        quizQuestion.setCurrentQuestionNumber(question.getCurrentQuestionNumber());
        quizQuestion.setQuestionContent(question.getQuestionContent());
        List<Option> optionResponses = new ArrayList<>();
        for(OptionResponse optionResponse : question.getOption()) {
            Option option = new Option();
            option.setOptionContent(optionResponse.getOptionContent());
            optionResponses.add(option);
        }
        quizQuestion.setOptions(optionResponses);
        quizQuestion.setAnswer(question.getAnswer());
        return quizQuestion;
    }

    private CreateQuestionResponse questionResponse(Question quizQuestion) {
        CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
        createQuestionResponse.setCurrentQuestionNumber(quizQuestion.getCurrentQuestionNumber());
        createQuestionResponse.setQuestionId(quizQuestion.getQuestionId());
        createQuestionResponse.setQuestionContent(quizQuestion.getQuestionContent());
        List<OptionResponse> optionResponses = new ArrayList<>();
        for(Option option : quizQuestion.getOptions()) {
            OptionResponse optionResponse = new OptionResponse();
            optionResponse.setOptionContent(option.getOptionContent());
            optionResponses.add(optionResponse);
        }
        createQuestionResponse.setOption(optionResponses);
        createQuestionResponse.setAnswer(quizQuestion.getAnswer());
        return createQuestionResponse;
    }




    @Override
    public UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest) {
        Quiz quiz = findQuizBy(updateQuizRequest.getQuizId());
        if(quiz == null){
            throw new IllegalArgumentException("A quiz doesn't exists");
        }
        quiz.setQuizId(updateQuizRequest.getQuizId());
        quiz.setQuizTitle(updateQuizRequest.getQuizName());

        questionService.updateQuestion(updateQuizRequest.getUpdateQuestionRequest());


        var updatedQuiz = quizRepository.save(quiz);


        UpdateQuizResponse updateQuizResponse = new UpdateQuizResponse();
        updateQuizResponse.setUpdateQuizId(updatedQuiz.getQuizId());
        updateQuizResponse.setUpdatedQuizName(updatedQuiz.getQuizTitle());



        return updateQuizResponse;
    }

    @Override
    public DeleteQuizResponse deleteQuiz(DeleteQuizRequest deleteQuizRequest) {
        Quiz quiz = findQuizBy(deleteQuizRequest.getQuizId());
        for (Question question : quiz.getQuestions()) {
            DeleteQuestionRequest deleteQuestionRequest = new DeleteQuestionRequest();
            deleteQuestionRequest.setQuestionId(question.getQuestionId());
            questionService.deleteQuestion(deleteQuestionRequest);
        }
        deleteQuizRequest.setQuizId(quiz.getQuizId());

        DeleteQuizResponse deleteQuizResponse = new DeleteQuizResponse();
        deleteQuizResponse.setQuizId(quiz.getQuizId());
        quizRepository.delete(quiz);
        return deleteQuizResponse;
    }

    private Quiz findQuizBy(String id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz does not exist"));
    }

    @Override
    public List<Question> getQuizQuestions(String quizId) {
        Quiz quiz = findQuizBy(quizId);
        return quiz.getQuestions();
    }

    public void resetQuestionCounter() {
        Counter counter = counterRepository.findById("questionCounter")
                .orElseGet(Counter::new);

        counter.setCurrentQuestionNumber(0);

        counterRepository.save(counter);
    }

    @PostConstruct
    public void initializeCounter() {
        counterRepository.findById("quizCounter").orElseGet(() -> {
            Counter counter = new Counter();
            counter.setId("quizCounter");
            counter.setCurrentQuizNumber(0);
            counterRepository.save(counter);
            return counter;
        });
    }
    public int getNextQuizNumber() {
        Counter counter = counterRepository.findById("quizCounter").orElseThrow(() -> new RuntimeException("Counter not found"));
        int currentQuizNumber = counter.getCurrentQuizNumber() + 1;
        counter.setCurrentQuizNumber(currentQuizNumber);
        counterRepository.save(counter);


        return currentQuizNumber;
    }




}

