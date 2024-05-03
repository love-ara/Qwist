package africa.semicolon.controller;

import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.DeleteQuizRequest;
import africa.semicolon.dto.request.UpdateQuizRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.service.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private QuizService quizService;

    @PostMapping("/create_quiz")
    public ResponseEntity<?> createQuiz(@RequestBody CreateQuizRequest quiz) {
        try{
            var response = quizService.createQuiz(quiz);
            return new ResponseEntity<>(new ApiResponse(true, response), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }
    @PostMapping("/update_quiz")
    public ResponseEntity<?> updateQuiz(@RequestBody UpdateQuizRequest quiz) {
        try{
            var response = quizService.updateQuiz(quiz);
            return new ResponseEntity<>(new ApiResponse(true, response), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }

    @DeleteMapping("/delete_quiz")
    public ResponseEntity<?> deleteQuiz(@RequestBody DeleteQuizRequest quiz) {
        try{
            var response = quizService.deleteQuiz(quiz);
            return new ResponseEntity<>(new ApiResponse(true, response), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }


}
