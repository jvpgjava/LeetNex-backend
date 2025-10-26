package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionProblem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private PracticeSession practiceSession;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "time_spent_minutes")
    private Integer timeSpentMinutes;
    
    @Column(name = "attempts_count")
    @Builder.Default
    private Integer attemptsCount = 0;
    
    @Column(name = "is_solved")
    @Builder.Default
    private Boolean isSolved = false;
    
    @Column(name = "problem_order")
    private Integer problemOrder;
    
    public enum ProblemStatus {
        NOT_STARTED, IN_PROGRESS, SOLVED, SKIPPED, FAILED
    }
}
