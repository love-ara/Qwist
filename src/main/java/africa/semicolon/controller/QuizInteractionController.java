package africa.semicolon.controller;


import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.UserAnswer;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.dto.response.QuizSession;
import africa.semicolon.service.services.QuizInteractionService;
import africa.semicolon.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/quiz_starter")
public class QuizInteractionController {

    private final QuizInteractionService quizInteractionService;

    @Autowired
    public QuizInteractionController(QuizInteractionService quizInteractionService) {
        this.quizInteractionService = quizInteractionService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startQuiz(@RequestBody UserSession userSession) {
        try{
            QuizSession session = quizInteractionService.startQuizSession(userSession);
            return new ResponseEntity<>(new ApiResponse(true, session), OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }

    }

    @GetMapping("/next_question")
    public ResponseEntity<?> getNextQuestion(@RequestBody UserSession userSession) {
        try {
            Question question = quizInteractionService.getNextQuestion(userSession);
            return new ResponseEntity<>(new ApiResponse(true, question), OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }

    @PostMapping("/submit_answer")
    public ResponseEntity<?> submitAnswer(@RequestBody UserAnswer userAnswer, UserSession session) {
        try{
            quizInteractionService.processAnswer(session, userAnswer);
            //return new ResponseEntity<>(new ApiResponse(true, userAnswer), OK);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }

    }

    @PostMapping("/next")
    public ResponseEntity<Void> moveToNextQuestion(@RequestBody UserSession session) {
        try{
            quizInteractionService.moveToNextQuestion(session);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(OK).build();
        }

    }
}

