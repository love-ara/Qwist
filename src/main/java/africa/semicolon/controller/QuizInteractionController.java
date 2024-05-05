package africa.semicolon.controller;

import africa.semicolon.dto.request.GetQuizRequest;
import africa.semicolon.dto.request.UserRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.service.services.QuizInteractionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/start")
@AllArgsConstructor
public class QuizInteractionController {
    private final QuizInteractionService quizInteractionService;

    @PostMapping ("/display/{quizPin}")
    public ResponseEntity<?> displayQuiz(@RequestBody UserRequest userRequest) {
        try {
            String quizPin = userRequest.getQuizPin();
            var quizResponse = quizInteractionService.displayQuiz(quizPin);
            if (quizResponse == null) {
                return new ResponseEntity<>(new ApiResponse(false, null), NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse(true, quizResponse), OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/submit/{quizPin}")
    public ResponseEntity<?> collectUserAnswersAndCalculateScore(@RequestBody GetQuizRequest getQuizRequest) {
        try {
            // Process the request and calculate the user's score
            var resultResponse = quizInteractionService.collectUserAnswersAndCalculateScore(getQuizRequest.getQuizPin(), getQuizRequest);

            // Return the result wrapped in an ApiResponse object with HTTP status OK (200)
            return new ResponseEntity<>(new ApiResponse(true, resultResponse), OK);
        } catch (Exception e) {
            // If there is an exception, return an ApiResponse object with an error message and HTTP status Internal Server Error (500)
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }
}

