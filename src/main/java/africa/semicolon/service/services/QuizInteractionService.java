package africa.semicolon.service.services;

import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.UserAnswer;

import africa.semicolon.dto.response.QuizSession;
import africa.semicolon.util.UserSession;
import org.springframework.stereotype.Service;


@Service
public interface QuizInteractionService{
    QuizSession startQuizSession(UserSession userSession);
   Question getNextQuestion(UserSession userSession);
    void processAnswer(UserSession session, UserAnswer userAnswer);
    void moveToNextQuestion(UserSession userSession);
}
