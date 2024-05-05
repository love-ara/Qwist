package africa.semicolon.service.impl;

import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.response.CreateQuestionResponse;
import africa.semicolon.dto.response.GetQuizResponse;
import africa.semicolon.dto.response.QuizResultResponse;
import africa.semicolon.service.services.QuizInteractionService;
import africa.semicolon.service.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizInteractionImpl implements QuizInteractionService {
    private final QuizService quizService;

    @Override
    public GetQuizResponse displayQuiz(String quizPin) {
        return quizService.getQuiz(quizPin);
    }

    @Override
    public QuizResultResponse collectUserAnswersAndCalculateScore(String quizPin, GetQuizRequest getQuizRequest) {
        // Retrieve the quiz using the provided quizPin
        GetQuizResponse quizResponse = quizService.getQuiz(quizPin);

        // Check if the quiz is valid and contains questions
        if (quizResponse == null || quizResponse.getGetQuestionResponse().isEmpty()) {
            QuizResultResponse quizResultResponse = new QuizResultResponse();
            quizResultResponse.setFinalScore(0);
            return quizResultResponse;
        }

        // Initialize variables to track score and feedback
        int userScore = 0;
        List<String> feedbackList = new ArrayList<>();

        // Iterate through each question in the quiz
        for (CreateQuestionResponse question : quizResponse.getGetQuestionResponse()) {
            // Retrieve the user's answer for the current question
            String userAnswer = getQuizRequest.getUserAnswers().get(question.getQuestionId());

            // Debug logs
            System.out.println("Processing question ID: " + question.getQuestionId());
            System.out.println("Correct answer: " + question.getAnswer());
            System.out.println("User answer: " + userAnswer);

            // Process the user's answer and calculate the score
            int score = processUserAnswer(question, userAnswer);
            userScore += score;

            // Generate feedback message based on the answer and score
            feedbackList.add(generateFeedback(question, userAnswer, score));
        }

        // Prepare the quiz result response with final score and feedback list
        QuizResultResponse quizResultResponse = new QuizResultResponse();
        quizResultResponse.setFinalScore(userScore);
        quizResultResponse.setFeedbackList(feedbackList);

        // Return the quiz result response
        return quizResultResponse;
    }

    // Generates feedback messages based on the question, user answer, and score
    private String generateFeedback(CreateQuestionResponse question, String userAnswer, int score) {
        if (score == -1) {
            return "Time's up for question: " + question.getQuestionContent();
        } else if (score > 0) {
            return "Correct answer to question: " + question.getQuestionContent();
        } else {
            return "Incorrect answer to question: " + question.getQuestionContent();
        }
    }

    // Processes the user's answer and calculates the score
    private int processUserAnswer(CreateQuestionResponse questionResponse, String userAnswer) {
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
            // Calculate time taken (you may want to pass this as an argument)
            long answerTime = System.currentTimeMillis() - startTime;

            // Calculate speed bonus if applicable
            int speedBonus = calculateSpeedBonus(answerTime);
            System.out.println("Score calculated: " + (baseScore + speedBonus));
            return baseScore + speedBonus;
        } else {
            return 0;
        }
    }


    // Calculates a speed bonus based on answer time
    private int calculateSpeedBonus(long answerTime) {
        long threshold = 30000; // 30 seconds threshold
        if (answerTime <= threshold) {
            return (int) ((threshold - answerTime) / 1000);
        }
        return 0;
    }

    // Handles a timeout scenario
    private int handleTimeout(CreateQuestionResponse questionResponse) {
        return -1; // Return a special score for timeout
    }
}
