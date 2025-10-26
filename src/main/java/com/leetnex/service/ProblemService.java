package com.leetnex.service;

import com.leetnex.dto.ProblemDTO;
import com.leetnex.mapper.ProblemMapper;
import com.leetnex.model.Problem;
import com.leetnex.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProblemService {
    
    private final ProblemRepository problemRepository;
    
    public Page<ProblemDTO> getProblemsWithFilters(Problem.Difficulty difficulty, 
                                                  Problem.Category category, 
                                                  String searchTerm, 
                                                  Pageable pageable) {
        Page<Problem> problems = problemRepository.findProblemsWithFilters(difficulty, category, searchTerm, pageable);
        return problems.map(ProblemMapper.INSTANCE::toDTO);
    }
    
    public Page<ProblemDTO> getPublicProblems(Pageable pageable) {
        Page<Problem> problems = problemRepository.findFreeProblems(pageable);
        return problems.map(ProblemMapper.INSTANCE::toDTO);
    }
    
    public ProblemDTO getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Problem not found"));
        return ProblemMapper.INSTANCE.toDTO(problem);
    }
    
    public ProblemDTO getRandomProblem(Problem.Difficulty difficulty, Problem.Category category) {
        List<Problem> problems;
        
        if (difficulty != null && category != null) {
            problems = problemRepository.findByDifficultyAndCategoryAndIsActiveTrue(difficulty, category, PageRequest.of(0, 100)).getContent();
        } else if (difficulty != null) {
            problems = problemRepository.findByDifficultyAndIsActiveTrue(difficulty, PageRequest.of(0, 100)).getContent();
        } else if (category != null) {
            problems = problemRepository.findByCategoryAndIsActiveTrue(category, PageRequest.of(0, 100)).getContent();
        } else {
            problems = problemRepository.findByIsActiveTrue(PageRequest.of(0, 100)).getContent();
        }
        
        if (problems.isEmpty()) {
            throw new RuntimeException("No problems found with the specified criteria");
        }
        
        Random random = new Random();
        Problem randomProblem = problems.get(random.nextInt(problems.size()));
        return ProblemMapper.INSTANCE.toDTO(randomProblem);
    }
    
    public Map<String, Object> getProblemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Count by difficulty
        Map<Problem.Difficulty, Long> difficultyCounts = new HashMap<>();
        for (Problem.Difficulty difficulty : Problem.Difficulty.values()) {
            long count = problemRepository.countByDifficulty(difficulty);
            difficultyCounts.put(difficulty, count);
        }
        stats.put("difficultyCounts", difficultyCounts);
        
        // Count by category
        Map<Problem.Category, Long> categoryCounts = new HashMap<>();
        for (Problem.Category category : Problem.Category.values()) {
            long count = problemRepository.countByCategory(category);
            categoryCounts.put(category, count);
        }
        stats.put("categoryCounts", categoryCounts);
        
        // Top problems by acceptance rate
        List<ProblemDTO> topByAcceptance = problemRepository.findTopProblemsByAcceptanceRate(PageRequest.of(0, 10))
            .stream()
            .map(ProblemMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
        stats.put("topByAcceptance", topByAcceptance);
        
        // Top problems by submissions
        List<ProblemDTO> topBySubmissions = problemRepository.findTopProblemsBySubmissions(PageRequest.of(0, 10))
            .stream()
            .map(ProblemMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
        stats.put("topBySubmissions", topBySubmissions);
        
        return stats;
    }
}
