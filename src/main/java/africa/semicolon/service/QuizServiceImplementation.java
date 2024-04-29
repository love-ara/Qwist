package africa.semicolon.service;

import africa.semicolon.data.model.Option;
import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.Quiz;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.data.repository.QuizRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.DeleteQuizResponse;
import africa.semicolon.dto.response.UpdateQuizResponse;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImplementation implements QuizService{
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private QuestionService questionService;


    @Override
    public CreateQuizResponse createQuiz(CreateQuizRequest createQuizRequest) {
        if(quizRepository.existsByQuizName(createQuizRequest.getQuizName())){
            throw new IllegalArgumentException("A quiz with this name already exists");
        }
        Quiz quiz = new Quiz();
        quiz.setQuizName(createQuizRequest.getQuizName());
        quiz.setQuizDescription(createQuizRequest.getQuizDescription());

        List<Question> questions = new ArrayList<>();

        for(CreateQuestionRequest createQuestionRequest : createQuizRequest.getCreateQuestionRequest()){
            Question question = createQuizQuestion(createQuestionRequest);

            var savedQuestion = questionRepository.save(question);
            questions.add(savedQuestion);
        }


        quiz.setQuestions(questions);
        var savedQuiz = quizRepository.save(quiz);

        CreateQuizResponse createQuizResponse = new CreateQuizResponse();
        createQuizResponse.setQuizId(savedQuiz.getQuizId());
        createQuizResponse.setQuizName(savedQuiz.getQuizName());
        createQuizResponse.setQuizDescription(savedQuiz.getQuizDescription());


        return createQuizResponse;
    }

    private static Question createQuizQuestion(CreateQuestionRequest createQuestionRequest) {
        Question question = new Question();
        question.setQuestionContent(createQuestionRequest.getQuestionContent());

        List<Option> options = new ArrayList<>();
        for(OptionRequest optionRequest : createQuestionRequest.getOption()) {
            Option option = new Option();
            option.setOptionContent(optionRequest.getOptionContent());
            options.add(option);
        }

        question.setOptions(options);
        question.setCorrectAnswer(createQuestionRequest.getAnswer());
        return question;
    }

    @Override
    public UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest) {
        var quiz = quizRepository.findByQuizName(updateQuizRequest.getQuizName());
        if(quiz == null){
            throw new IllegalArgumentException("A quiz doesn't exists");
        }
        quiz.setQuizName(updateQuizRequest.getQuizName());
        quiz.setQuizDescription(updateQuizRequest.getQuizDescription());


        List <Question> questionList = new ArrayList<>();
        for(UpdateQuestionRequest updateQuestionRequest : updateQuizRequest.getUpdateQuestionRequest()){
            questionService.updateQuestion(updateQuestionRequest);
            Question updatedQuestion = new Question();
            updatedQuestion.setQuestionContent(updateQuestionRequest.getQuestionContent());
            updatedQuestion.setQuestionId(updateQuestionRequest.getQuestionId());
            questionList.add(updatedQuestion);
        }

        quiz.setQuestions(questionList);
        var updatedQuiz = quizRepository.save(quiz);

        UpdateQuizResponse updateQuizResponse = new UpdateQuizResponse();
        updateQuizResponse.setUpdateQuizId(updatedQuiz.getQuizId());
        updateQuizResponse.setUpdatedQuizName(updatedQuiz.getQuizName());
        updateQuizResponse.setUpdatedQuizDescription(updatedQuiz.getQuizDescription());

        return updateQuizResponse;
    }

    @Override
    public DeleteQuizResponse deleteQuiz(DeleteQuizRequest deleteQuizRequest) {
        Quiz quiz = quizRepository.findById(deleteQuizRequest.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz does not exist"));
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
}

