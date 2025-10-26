package com.leetnex.dto;

import com.leetnex.model.PracticeSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeSessionDTO {
    private Long id;
    private Long userId;
    private String sessionName;
    private PracticeSession.SessionType sessionType;
    private PracticeSession.SessionStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer durationMinutes;
    private Boolean timerEnabled;
    private Boolean cameraEnabled;
    private Integer problemsSolved;
    private Integer totalProblems;
    private String sessionNotes;
    private List<SessionProblemDTO> sessionProblems;
    private List<SubmissionDTO> submissions;
}
