package africa.semicolon.service.impl;

import africa.semicolon.data.model.*;
import africa.semicolon.data.repository.CounterRepository;
import africa.semicolon.data.repository.QuizRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;


import africa.semicolon.service.services.QuestionService;
import africa.semicolon.service.services.QuizService;
import africa.semicolon.util.QuizPinGenerator;
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
        resetQuestionCounter();

        Quiz quiz = new Quiz();

        quiz.setQuizTitle(createQuizRequest.getQuizTitle());

        String pin = QuizPinGenerator.generateQuizPin();
        quiz.setQuizPin(pin);
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
        createQuizResponse.setQuizPin(savedQuiz.getQuizPin());
        createQuizResponse.setQuizId(savedQuiz.getQuizId());
        createQuizResponse.setQuizTitle(savedQuiz.getQuizTitle());
        createQuizResponse.setCreateQuestionResponse(questionResponses);


            return createQuizResponse;
    }

    private Question createQuestion(CreateQuestionRequest createQuestionRequest) {
        var question = questionService.createQuestion(createQuestionRequest);
        Question quizQuestion = new Question();
        quizQuestion.setQuestionId(question.getQuestionId());
        quizQuestion.setTimeLimit(question.getTimeLimit());
        quizQuestion.setQuestionType(QuestionType.valueOf(question.getQuestionType()));
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
        createQuestionResponse.setTimeLimit(quizQuestion.getTimeLimit());
        createQuestionResponse.setCurrentQuestionNumber(quizQuestion.getCurrentQuestionNumber());
        createQuestionResponse.setQuestionId(quizQuestion.getQuestionId());
        questionAndOptionsResponse(quizQuestion, createQuestionResponse);
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
        quiz.setQuizTitle(updateQuizRequest.getQuizTitle());

        questionService.updateQuestion(updateQuizRequest.getUpdateQuestionRequest());


        var updatedQuiz = quizRepository.save(quiz);


        UpdateQuizResponse updateQuizResponse = new UpdateQuizResponse();
        updateQuizResponse.setUpdateQuizId(updatedQuiz.getQuizId());
        updateQuizResponse.setUpdatedQuizTitle(updatedQuiz.getQuizTitle());



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

    @Override
    public GetQuizResponse getQuiz(String quizPin){
        Quiz quiz = quizRepository.findByQuizPin(quizPin);
        if(quiz == null){
            throw new IllegalArgumentException("A quiz doesn't exist");
        }

        GetQuizResponse getQuizResponse = new GetQuizResponse();
        getQuizResponse.setQuizTitle(quiz.getQuizTitle());
        List<CreateQuestionResponse> createQuestionResponses = new ArrayList<>();
        for(Question question : quiz.getQuestions()) {
            CreateQuestionResponse createQuestionResponse = getQuestion(question);

            createQuestionResponses.add(createQuestionResponse);
        }
        getQuizResponse.setGetQuestionResponse(createQuestionResponses);
        return getQuizResponse;
    }

    private static CreateQuestionResponse getQuestion(Question question) {
        CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
        createQuestionResponse.setCurrentQuestionNumber(question.getCurrentQuestionNumber());
        questionAndOptionsResponse(question, createQuestionResponse);
        return createQuestionResponse;
    }

    private static void questionAndOptionsResponse(Question question, CreateQuestionResponse createQuestionResponse) {
        createQuestionResponse.setQuestionId(question.getQuestionId());
        createQuestionResponse.setQuestionContent(question.getQuestionContent());
        createQuestionResponse.setQuestionType(String.valueOf(question.getQuestionType()));
        List<OptionResponse> optionResponses = new ArrayList<>();
        for (Option option : question.getOptions()) {
            OptionResponse optionResponse = new OptionResponse();
            optionResponse.setOptionContent(option.getOptionContent());
            optionResponses.add(optionResponse);
        }
        createQuestionResponse.setOption(optionResponses);
        createQuestionResponse.setTimeLimit(question.getTimeLimit());
        createQuestionResponse.setAnswer(question.getAnswer());
    }


}

