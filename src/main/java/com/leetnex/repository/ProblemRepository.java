package com.leetnex.repository;

import com.leetnex.model.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
    Page<Problem> findByIsActiveTrue(Pageable pageable);
    
    Page<Problem> findByDifficultyAndIsActiveTrue(Problem.Difficulty difficulty, Pageable pageable);
    
    Page<Problem> findByCategoryAndIsActiveTrue(Problem.Category category, Pageable pageable);
    
    Page<Problem> findByDifficultyAndCategoryAndIsActiveTrue(
        Problem.Difficulty difficulty, 
        Problem.Category category, 
        Pageable pageable
    );
    
    @Query("SELECT p FROM Problem p WHERE p.isActive = true AND " +
           "(:difficulty IS NULL OR p.difficulty = :difficulty) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:searchTerm IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Problem> findProblemsWithFilters(
        @Param("difficulty") Problem.Difficulty difficulty,
        @Param("category") Problem.Category category,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );
    
    @Query("SELECT p FROM Problem p WHERE p.isActive = true AND p.isPremium = false")
    Page<Problem> findFreeProblems(Pageable pageable);
    
    @Query("SELECT p FROM Problem p WHERE p.leetcodeNumber = :leetcodeNumber")
    Optional<Problem> findByLeetcodeNumber(@Param("leetcodeNumber") Integer leetcodeNumber);
    
    @Query("SELECT p FROM Problem p WHERE p.isActive = true ORDER BY p.acceptanceRate DESC")
    List<Problem> findTopProblemsByAcceptanceRate(Pageable pageable);
    
    @Query("SELECT p FROM Problem p WHERE p.isActive = true ORDER BY p.totalSubmissions DESC")
    List<Problem> findTopProblemsBySubmissions(Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Problem p WHERE p.difficulty = :difficulty AND p.isActive = true")
    long countByDifficulty(@Param("difficulty") Problem.Difficulty difficulty);
    
    @Query("SELECT COUNT(p) FROM Problem p WHERE p.category = :category AND p.isActive = true")
    long countByCategory(@Param("category") Problem.Category category);
}
