package com.leetnex.controller;

import com.leetnex.dto.ProblemDTO;
import com.leetnex.model.Problem;
import com.leetnex.repository.ProblemRepository;
import com.leetnex.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProblemController {
    
    private final ProblemService problemService;
    private final ProblemRepository problemRepository;
    
    @GetMapping
    public ResponseEntity<Page<ProblemDTO>> getAllProblems(
            @RequestParam(required = false) Problem.Difficulty difficulty,
            @RequestParam(required = false) Problem.Category category,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        
        Page<ProblemDTO> problems = problemService.getProblemsWithFilters(difficulty, category, search, pageable);
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/public")
    public ResponseEntity<Page<ProblemDTO>> getPublicProblems(Pageable pageable) {
        Page<ProblemDTO> problems = problemService.getPublicProblems(pageable);
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable Long id) {
        try {
            ProblemDTO problem = problemService.getProblemById(id);
            return ResponseEntity.ok(problem);
        } catch (Exception e) {
            log.error("Error getting problem", e);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/random")
    public ResponseEntity<ProblemDTO> getRandomProblem(
            @RequestParam(required = false) Problem.Difficulty difficulty,
            @RequestParam(required = false) Problem.Category category) {
        try {
            ProblemDTO problem = problemService.getRandomProblem(difficulty, category);
            return ResponseEntity.ok(problem);
        } catch (Exception e) {
            log.error("Error getting random problem", e);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<Problem.Category>> getCategories() {
        List<Problem.Category> categories = List.of(Problem.Category.values());
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/difficulties")
    public ResponseEntity<List<Problem.Difficulty>> getDifficulties() {
        List<Problem.Difficulty> difficulties = List.of(Problem.Difficulty.values());
        return ResponseEntity.ok(difficulties);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProblemStats() {
        Map<String, Object> stats = problemService.getProblemStatistics();
        return ResponseEntity.ok(stats);
    }
}
