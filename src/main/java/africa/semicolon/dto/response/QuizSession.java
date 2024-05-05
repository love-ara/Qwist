package africa.semicolon.dto.response;

import africa.semicolon.data.model.Question;
import africa.semicolon.data.model.UserAnswer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Data
public class QuizSession {
    private String quizId;
    private String quizPin;
    private String hostId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SessionState sessionState;
    private int questionIndex;
    private final Map<String, Integer> participantScores = new HashMap<>();
    private final Map<String, List<UserAnswer>> participantAnswers = new HashMap<>();
    private Set<String> participants;
    private Question currentQuestion;
    private int currentQuestionNumber;

    public enum SessionState {
        READY,
        IN_PROGRESS,
        PAUSED,
        COMPLETED
    }


    public QuizSession() {
        this.sessionState = SessionState.READY;
    }

    public void start() {
        this.sessionState = SessionState.IN_PROGRESS;
        this.startTime = LocalDateTime.now();
    }

    public void pause() {
        if (this.sessionState == SessionState.IN_PROGRESS) {
            this.sessionState = SessionState.PAUSED;
        }
    }

    public void end() {
        this.sessionState = SessionState.COMPLETED;
        this.endTime = LocalDateTime.now();
    }


}
