package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgrammingLanguage language;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;
    
    @Column(name = "execution_time")
    private Long executionTime; // in milliseconds
    
    @Column(name = "memory_usage")
    private Long memoryUsage; // in bytes
    
    @Column(name = "runtime_error")
    private String runtimeError;
    
    @Column(name = "compile_error")
    private String compileError;
    
    @Column(name = "test_cases_passed")
    private Integer testCasesPassed;
    
    @Column(name = "total_test_cases")
    private Integer totalTestCases;
    
    @Column(name = "submitted_at")
    @Builder.Default
    private LocalDateTime submittedAt = LocalDateTime.now();
    
    @Column(name = "is_accepted")
    private Boolean isAccepted;
    
    @Column(name = "session_id")
    private String sessionId; // For practice sessions
    
    public enum ProgrammingLanguage {
        JAVA, PYTHON, JAVASCRIPT, CPP, C, CSHARP, GO, RUST, SWIFT, KOTLIN
    }
    
    public enum SubmissionStatus {
        PENDING, RUNNING, ACCEPTED, WRONG_ANSWER, RUNTIME_ERROR, 
        COMPILE_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED
    }
}
