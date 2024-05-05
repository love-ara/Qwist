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
            var resultResponse = quizInteractionService.collectUserAnswersAndCalculateScore(getQuizRequest.getQuizPin(), getQuizRequest);

            return new ResponseEntity<>(new ApiResponse(true, resultResponse), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), INTERNAL_SERVER_ERROR);
        }
    }
}

