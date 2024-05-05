package africa.semicolon.service.impl;

import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.response.*;
import africa.semicolon.service.services.QuizInteractionService;
import africa.semicolon.service.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class QuizInteractionImpl implements QuizInteractionService {
    private final QuizService quizService;

    @Override
    public ViewQuizResponse displayQuiz(String quizPin) {
        return quizService.viewQuiz(quizPin);
    }

    @Override
    public QuizResultResponse collectUserAnswersAndCalculateScore(String quizPin, GetQuizRequest getQuizRequest) {
        GetQuizResponse quizResponse = quizService.getQuiz(getQuizRequest);


        if (quizResponse == null || quizResponse.getGetQuestionResponse().isEmpty()) {
            QuizResultResponse quizResultResponse = new QuizResultResponse();
            quizResultResponse.setFinalScore(0);
            return quizResultResponse;
        }

        int userScore = 0;
        List<String> feedbackList = new ArrayList<>();

        for (GetQuestionResponse question : quizResponse.getGetQuestionResponse()) {
            String userAnswer = getQuizRequest.getUserAnswers().get(question.getQuestionId());

            System.out.println("Processing question ID: " + question.getQuestionId());
            System.out.println("Correct answer: " + question.getAnswer());
            System.out.println("User answer: " + userAnswer);

            int score = processUserAnswer(question, userAnswer);
            userScore += score;

            feedbackList.add(generateFeedback(question, userAnswer, score));
        }

        QuizResultResponse quizResultResponse = new QuizResultResponse();
        quizResultResponse.setFinalScore(userScore);
        quizResultResponse.setFeedbackList(feedbackList);

        return quizResultResponse;
    }

    private String generateFeedback(GetQuestionResponse question, String userAnswer, int score) {
        if (score == -1) {
            return "Time's up for question: " + question.getQuestionContent();
        } else if (score > 0) {
            return "Correct answer!";
        } else {
            return format("Incorrect answer to question:%s %nYour answer:%s", question.getQuestionContent(), userAnswer);
        }
    }

    private int processUserAnswer(GetQuestionResponse questionResponse, String userAnswer) {
        int baseScore = 1;
        String correctAnswer = questionResponse.getAnswer();

        System.out.println("Question: " + questionResponse.getQuestionContent());
        System.out.println("Correct Answer: " + correctAnswer);
        System.out.println("User Answer: " + userAnswer);

        if (correctAnswer == null) {
            System.out.println("Correct answer is null for question ID: " + questionResponse.getQuestionId());
            return 0;
        }

        if (correctAnswer.equalsIgnoreCase(userAnswer)) {
            long startTime = System.currentTimeMillis();
            long answerTime = System.currentTimeMillis() - startTime;

            int speedBonus = calculateSpeedBonus(answerTime);
            System.out.println("Score calculated: " + (baseScore + speedBonus));
            return baseScore + speedBonus;
        } else {
            return 0;
        }
    }


    private int calculateSpeedBonus(long answerTime) {
        long threshold = 30000;
        if (answerTime <= threshold) {
            return (int) ((threshold - answerTime) / 1000);
        }
        return 0;
    }

    private int handleTimeout(CreateQuestionResponse questionResponse) {
        return -1;
    }
}
