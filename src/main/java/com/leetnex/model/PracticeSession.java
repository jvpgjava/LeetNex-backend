package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "practice_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "session_name")
    private String sessionName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionType sessionType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;
    
    @Column(name = "started_at")
    @Builder.Default
    private LocalDateTime startedAt = LocalDateTime.now();
    
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Column(name = "timer_enabled")
    @Builder.Default
    private Boolean timerEnabled = false;
    
    @Column(name = "camera_enabled")
    @Builder.Default
    private Boolean cameraEnabled = false;
    
    @Column(name = "problems_solved")
    @Builder.Default
    private Integer problemsSolved = 0;
    
    @Column(name = "total_problems")
    private Integer totalProblems;
    
    @Column(name = "session_notes", columnDefinition = "TEXT")
    private String sessionNotes;
    
    @OneToMany(mappedBy = "practiceSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SessionProblem> sessionProblems;
    
    // Removed problematic submissions mapping - will be handled via sessionId field
    
    public enum SessionType {
        PRACTICE, MOCK_INTERVIEW, TIMED_CHALLENGE, CUSTOM
    }
    
    public enum SessionStatus {
        ACTIVE, PAUSED, COMPLETED, CANCELLED
    }
}
