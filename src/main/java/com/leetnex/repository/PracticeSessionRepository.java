package com.leetnex.repository;

import com.leetnex.model.PracticeSession;
import com.leetnex.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
    
    Page<PracticeSession> findByUserOrderByStartedAtDesc(User user, Pageable pageable);
    
    List<PracticeSession> findByUserAndStatus(User user, PracticeSession.SessionStatus status);
    
    @Query("SELECT ps FROM PracticeSession ps WHERE ps.user = :user AND ps.startedAt >= :startDate AND ps.startedAt <= :endDate ORDER BY ps.startedAt DESC")
    List<PracticeSession> findByUserAndDateRange(
        @Param("user") User user, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT ps FROM PracticeSession ps WHERE ps.user = :user AND ps.sessionType = :sessionType ORDER BY ps.startedAt DESC")
    List<PracticeSession> findByUserAndSessionType(@Param("user") User user, @Param("sessionType") PracticeSession.SessionType sessionType);
    
    @Query("SELECT COUNT(ps) FROM PracticeSession ps WHERE ps.user = :user AND ps.status = :status")
    long countByUserAndStatus(@Param("user") User user, @Param("status") PracticeSession.SessionStatus status);
    
    @Query("SELECT AVG(ps.durationMinutes) FROM PracticeSession ps WHERE ps.user = :user AND ps.status = 'COMPLETED'")
    Double findAverageSessionDurationByUser(@Param("user") User user);
    
    @Query("SELECT ps FROM PracticeSession ps WHERE ps.user = :user AND ps.cameraEnabled = true ORDER BY ps.startedAt DESC")
    List<PracticeSession> findSessionsWithCameraByUser(@Param("user") User user);
}
