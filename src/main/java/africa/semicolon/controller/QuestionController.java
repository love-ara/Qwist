package africa.semicolon.controller;


import africa.semicolon.dto.request.CreateQuestionRequest;
import africa.semicolon.dto.request.DeleteQuestionRequest;
import africa.semicolon.dto.request.UpdateQuestionRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.service.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {
    private QuestionService questionService;

    @PostMapping("/create_question")
    public ResponseEntity<?> createQuestion(CreateQuestionRequest question) {
        try {
            var questionResponse = questionService.createQuestion(question);
            return new ResponseEntity<>(new ApiResponse(true, questionResponse), OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/update_question")
    public ResponseEntity<?> updateQuestion(UpdateQuestionRequest question) {
        try{
            var updateQuestionResponse = questionService.updateQuestion(question);
            return new ResponseEntity<>(new ApiResponse(true, updateQuestionResponse), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_question")
    public ResponseEntity<?> deleteQuestion(DeleteQuestionRequest question) {
        try{
            var deleteQuestion = questionService.deleteQuestion(question);
            return new ResponseEntity<>(new ApiResponse(true, deleteQuestion), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

}
