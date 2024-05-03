package africa.semicolon.dto.response;

import lombok.Data;

@Data
public class QuestionResponse {

}



/**

 QuizSession:
 Represents a user’s session during a quiz.
 Contains relevant fields to manage the session, such as:
 User ID
 Quiz ID
 Start time
 Duration (if needed)
 Any other session-related data
 You can add more fields based on your requirements.
 QuestionResponse:
 Represents the response containing information about the next question.
 Contains relevant fields for presenting the question to the user, such as:
 Question text
 Options (if multiple-choice)
 Question type (e.g., multiple-choice, true/false, open-ended)
 Any other relevant details
 You can customize this class to match your specific question format.
 QuizResponse:
 Represents the response after the user submits an answer.
 Contains fields related to the user’s answer, correctness, and any additional feedback:
 User’s answer
 Correct answer (if applicable)
 Feedback (e.g., “Correct!” or “Incorrect. The correct answer is…”)
 Score (if scoring is involved)
 **/