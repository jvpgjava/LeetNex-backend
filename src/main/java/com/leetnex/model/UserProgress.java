package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Problem.Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Problem.Category category;

    @Column(name = "is_solved")
    @Builder.Default
    private Boolean isSolved = false;

    @Column(name = "first_solved_at")
    private LocalDateTime firstSolvedAt;

    @Column(name = "last_attempted_at")
    private LocalDateTime lastAttemptedAt;

    @Column(name = "attempts_count")
    @Builder.Default
    private Integer attemptsCount = 0;

    @Column(name = "best_execution_time")
    private Long bestExecutionTime; // in milliseconds

    @Column(name = "best_memory_usage")
    private Long bestMemoryUsage; // in bytes

    @Column(name = "favorite_language")
    @Enumerated(EnumType.STRING)
    private Submission.ProgrammingLanguage favoriteLanguage;
}
