package africa.semicolon.service;

import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.UserAnswer;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.data.repository.UserAnswerRepository;
import africa.semicolon.dto.response.QuizSession;
import africa.semicolon.service.impl.QuizInteractionServiceImplementation;
import africa.semicolon.util.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuizInteractionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserAnswerRepository userAnswerRepository;

    @InjectMocks
    private QuizInteractionServiceImplementation quizService;

    private UserSession userSession;
    private Question question;
    private UserAnswer userAnswer;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        userSession = new UserSession();
        userSession.setUserId("testUserId");
        userSession.setQuizId("testQuizId");
        userSession.setCurrentQuestionNumber(1);

        question = new Question();
        question.setQuestionId("1");
        question.setQuestionContent("What is the capital of France?");
        question.setAnswer("Paris");

        userAnswer = new UserAnswer();
        userAnswer.setUserAnswer("Paris");
    }

    @Test
    public void testStartQuizSession() {
        QuizSession session = quizService.startQuizSession(userSession);

        assertEquals(userSession.getUserId(), session.getHostId());
        assertEquals(userSession.getQuizId(), session.getQuizId());
    }

    @Test
    public void testGetNextQuestion() {
        when(questionRepository.findByCurrentQuestionNumber(1)).thenReturn(question);

        Question result = quizService.getNextQuestion(userSession);

        assertEquals(question, result);
        verify(questionRepository, times(1)).findByCurrentQuestionNumber(1);
    }

    @Test
    public void testProcessAnswer() {
        when(questionRepository.findByCurrentQuestionNumber(1)).thenReturn(question);
        when(userAnswerRepository.save(userAnswer)).thenReturn(userAnswer);

        quizService.processAnswer(userSession, userAnswer);

        assertEquals(1, userSession.getTotalScore());
        assertEquals(2, userSession.getCurrentQuestionNumber());
        verify(userAnswerRepository, times(1)).save(userAnswer);
    }

    @Test
    public void testValidateAnswer() {
        boolean isCorrect = quizService.validateAnswer(userAnswer, question);


        assertTrue(isCorrect, "Answer should be valid");
    }

    @Test
    public void testMoveToNextQuestion() {
        quizService.moveToNextQuestion(userSession);

        assertEquals(2, userSession.getCurrentQuestionNumber());
    }
}
