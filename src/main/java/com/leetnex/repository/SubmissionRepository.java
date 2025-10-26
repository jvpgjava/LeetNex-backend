package com.leetnex.repository;

import com.leetnex.model.Submission;
import com.leetnex.model.User;
import com.leetnex.model.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Page<Submission> findByUserOrderBySubmittedAtDesc(User user, Pageable pageable);

    Page<Submission> findByProblemOrderBySubmittedAtDesc(Problem problem, Pageable pageable);

    List<Submission> findByUserAndProblemOrderBySubmittedAtDesc(User user, Problem problem);

    @Query("SELECT s FROM Submission s WHERE s.user = :user AND s.status = :status ORDER BY s.submittedAt DESC")
    Page<Submission> findByUserAndStatus(@Param("user") User user, @Param("status") Submission.SubmissionStatus status,
            Pageable pageable);

    @Query("SELECT s FROM Submission s WHERE s.user = :user AND s.isAccepted = true ORDER BY s.submittedAt DESC")
    List<Submission> findAcceptedSubmissionsByUser(@Param("user") User user);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.user = :user")
    long countByUser(@Param("user") User user);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.user = :user AND s.isAccepted = true")
    long countAcceptedSubmissionsByUser(@Param("user") User user);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem = :problem AND s.isAccepted = true")
    long countAcceptedSubmissionsByProblem(@Param("problem") Problem problem);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem = :problem")
    long countTotalSubmissionsByProblem(@Param("problem") Problem problem);

    @Query("SELECT s FROM Submission s WHERE s.user = :user AND s.submittedAt >= :startDate AND s.submittedAt <= :endDate ORDER BY s.submittedAt DESC")
    List<Submission> findSubmissionsByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s.language, COUNT(s) FROM Submission s WHERE s.user = :user GROUP BY s.language")
    List<Object[]> countSubmissionsByLanguage(@Param("user") User user);

    @Query("SELECT s FROM Submission s WHERE s.sessionId = :sessionId ORDER BY s.submittedAt DESC")
    List<Submission> findBySessionId(@Param("sessionId") String sessionId);
}
