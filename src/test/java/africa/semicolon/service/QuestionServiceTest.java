package africa.semicolon.service;

import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.DeleteQuestionRequest;
import africa.semicolon.dto.request.OptionRequest;
import africa.semicolon.dto.request.UpdateQuestionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.gen5.api.Assertions.*;

@SpringBootTest
public class QuestionServiceTest {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    private CreateQuestionRequest createQuestionRequest;

    @Autowired
    public QuestionServiceTest(QuestionService questionService, QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @BeforeEach
    public void setUp() {
        questionRepository.deleteAll();

        createQuestionRequest = new CreateQuestionRequest();
        createQuestionRequest.setQuestionContent("Question content");
        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setOptionContent("Option content");
        createQuestionRequest.setOption(List.of(optionRequest));
        createQuestionRequest.setAnswer("Answer");
    }
    @Test
    public void questionCanBeCreatedTest(){
        questionService.createQuestion(createQuestionRequest);
        assertThat(questionRepository.count(), is(1L));
    }
    @Test
    public void noRepeatedQuestionTest(){
       questionService.createQuestion(createQuestionRequest);
       assertThrows(IllegalArgumentException.class, ()-> questionService.createQuestion(createQuestionRequest));
    }

    @Test
    public void testThatQuestionCanBeUpdated(){
        var question = questionService.createQuestion(createQuestionRequest);
        assertThat(questionRepository.findById(question.getQuestionId()).get().getQuestionContent(), is("Question content"));

        UpdateQuestionRequest updateQuestionRequest = new UpdateQuestionRequest();
        updateQuestionRequest.setQuestionId(question.getQuestionId());
        updateQuestionRequest.setQuestionContent("Updated question");
        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setOptionContent("Updated option content");
        updateQuestionRequest.setOptions(List.of(optionRequest));
        updateQuestionRequest.setAnswer("Updated correct answer");
        questionService.updateQuestion(updateQuestionRequest);

        assertThat(questionRepository.count(), is(1L));
        assertThat(questionRepository.findById(question.getQuestionId()).get().getQuestionContent(), is("Updated question"));

        assertTrue(questionRepository.existsByQuestionContent("Updated question"));
    }

    @Test
    public void  questionCanBeDeletedTest(){
        var question = questionService.createQuestion(createQuestionRequest);
        assertThat(questionRepository.count(), is(1L));

        DeleteQuestionRequest deleteQuestionRequest = new DeleteQuestionRequest();
        deleteQuestionRequest.setQuestionId(question.getQuestionId());
        questionService.deleteQuestion(deleteQuestionRequest);

        assertThat(questionRepository.count(), is(0L));
    }


}
