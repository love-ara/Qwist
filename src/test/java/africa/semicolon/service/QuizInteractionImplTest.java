package africa.semicolon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.response.*;
import africa.semicolon.service.impl.QuizInteractionImpl;
import africa.semicolon.service.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class QuizInteractionImplTest {

    @Mock
    private QuizService mockQuizService;

    private QuizInteractionImpl quizInteraction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizInteraction = new QuizInteractionImpl(mockQuizService);
    }

    @Test
    public void testDisplayQuiz() {
        String quizPin = "sampleQuizPin";
        GetQuizRequest getQuizRequest = new  GetQuizRequest();
        getQuizRequest.setQuizPin(quizPin);
        GetQuizResponse getQuizResponse = createSampleGetQuizResponse();

        when(mockQuizService.getQuiz(getQuizRequest)).thenReturn(getQuizResponse);

        ViewQuizResponse response = quizInteraction.displayQuiz(quizPin);

        assertEquals(getQuizResponse.getGetQuestionResponse().size(), response.getViewQuizQuestionResponses().size());
        assertEquals(getQuizResponse, response);
    }

    @Test
    public void testCollectUserAnswersAndCalculateScore() {
        String quizPin = "sampleQuizPin";
        GetQuizRequest getQuizRequest = new GetQuizRequest();
        getQuizRequest.setQuizPin(quizPin);

        GetQuizResponse getQuizResponse = createSampleGetQuizResponse();
        when(mockQuizService.getQuiz(getQuizRequest)).thenReturn(getQuizResponse);

        Map<String, String> userAnswers = new HashMap<>();
        for (GetQuestionResponse question : getQuizResponse.getGetQuestionResponse()) {
            userAnswers.put(question.getQuestionId(), question.getAnswer());
        }
        getQuizRequest.setUserAnswers(userAnswers);

        QuizResultResponse resultResponse = quizInteraction.collectUserAnswersAndCalculateScore(quizPin, getQuizRequest);


        int expectedScore = 62;
        assertEquals(expectedScore, resultResponse.getFinalScore());


        assertEquals(userAnswers.size(), resultResponse.getFeedbackList().size());
    }

    private GetQuizResponse createSampleGetQuizResponse() {
        GetQuestionResponse question1 = new GetQuestionResponse();
        question1.setQuestionId("q1");
        question1.setQuestionContent("What is 2 + 2?");
        question1.setAnswer("4");
        question1.setTimeLimit(10000);
        OptionResponse optionResponse = new OptionResponse();
        optionResponse.setOptionContent("7");
        optionResponse.setOptionContent("4");
        question1.setOption(Arrays.asList(optionResponse, optionResponse));

        GetQuestionResponse question2 = new GetQuestionResponse();
        question2.setQuestionId("q2");
        question2.setQuestionContent("What is the capital of France?");
        question2.setAnswer("Paris");
        question2.setTimeLimit(10000);
        OptionResponse optionResponse1 = new OptionResponse();
        optionResponse1.setOptionContent("paris");
        optionResponse1.setOptionContent("London");
        question2.setOption(Arrays.asList(optionResponse1, optionResponse1));
        GetQuizResponse getQuizResponse = new GetQuizResponse();
        getQuizResponse.setGetQuestionResponse(Arrays.asList(question1, question2));

        return getQuizResponse;
    }
}










