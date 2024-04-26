package africa.semicolon.service;

import africa.semicolon.data.model.Option;
import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.Quiz;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.data.repository.QuizRepository;
import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.OptionRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.UpdateQuizResponse;
import lombok.AllArgsConstructor;

import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImplementation implements QuizService{
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;


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
            Question question = new Question();
            question.setQuestionContent(createQuestionRequest.getQuestionContent());

            List<Option> options = new ArrayList<>();
            for(OptionRequest optionRequest :createQuestionRequest.getOption()) {
                Option option = new Option();
                option.setOptionContent(optionRequest.getOptionContent());
                options.add(option);
            }

            question.setOptions(options);
            question.setCorrectAnswer(createQuestionRequest.getAnswer());


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

    @Override
    public UpdateQuizResponse updateQuiz(UpdateQuizRequest updateQuizRequest) {
        return null;
    }


}

