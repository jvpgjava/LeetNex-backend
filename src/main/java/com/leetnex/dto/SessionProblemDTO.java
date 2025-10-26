package com.leetnex.dto;

import com.leetnex.model.SessionProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionProblemDTO {
    private Long id;
    private Long sessionId;
    private Long problemId;
    private String problemTitle;
    private ProblemDTO problem;
    private SessionProblem.ProblemStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer timeSpentMinutes;
    private Integer attemptsCount;
    private Boolean isSolved;
    private Integer problemOrder;
}
