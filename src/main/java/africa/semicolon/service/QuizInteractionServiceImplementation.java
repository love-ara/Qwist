package africa.semicolon.service;

import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.UserAnswer;
import africa.semicolon.data.repository.QuestionRepository;
import africa.semicolon.data.repository.UserAnswerRepository;
import africa.semicolon.dto.response.QuizSession;
import africa.semicolon.util.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuizInteractionServiceImplementation implements QuizInteractionService{
    private QuestionRepository questionRepository;
    private UserAnswerRepository userAnswerRepository;

    @Override
    public QuizSession startQuizSession(UserSession userSession) {
        QuizSession quizSession = new QuizSession();
        quizSession.setQuizId(userSession.getQuizId());
        quizSession.setHostId(userSession.getUserId());
        return quizSession;
    }

    @Override
    public Question getNextQuestion(UserSession session) {
        int currentQuestionNumber = session.getCurrentQuestionNumber();
        return questionRepository.findByCurrentQuestionNumber(currentQuestionNumber);
    }

    @Override
    public void processAnswer(UserSession session, UserAnswer userAnswer) {
        Question question = getNextQuestion(session);
        boolean isCorrect = validateAnswer(userAnswer, question);
        if(isCorrect){
            session.increaseScore();
        }
        session.increaseQuestionNumber();
        userAnswerRepository.save(userAnswer);
    }
    public boolean validateAnswer(UserAnswer answer, Question question) {
        if(!question.getAnswer().contains(answer.getUserAnswer())){
            return false;
        }

        return question.getAnswer().contains(answer.getUserAnswer());
    }

    @Override
    public void moveToNextQuestion(UserSession session){
        session.increaseQuestionNumber();
    }

}
