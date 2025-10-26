package com.leetnex.controller;

import com.leetnex.dto.SubmissionDTO;
import com.leetnex.model.Submission;
import com.leetnex.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SubmissionController {
    
    private final SubmissionService submissionService;
    
    @PostMapping
    public ResponseEntity<?> submitCode(@RequestBody Map<String, Object> submissionRequest,
                                     Authentication authentication) {
        try {
            String username = authentication.getName();
            String code = (String) submissionRequest.get("code");
            Long problemId = Long.valueOf(submissionRequest.get("problemId").toString());
            String language = (String) submissionRequest.get("language");
            String sessionId = (String) submissionRequest.get("sessionId");
            
            SubmissionDTO submission = submissionService.submitCode(
                username, problemId, code, Submission.ProgrammingLanguage.valueOf(language), sessionId
            );
            
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            log.error("Error submitting code", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<SubmissionDTO>> getUserSubmissions(
            Authentication authentication,
            @RequestParam(required = false) Submission.SubmissionStatus status,
            Pageable pageable) {
        try {
            String username = authentication.getName();
            Page<SubmissionDTO> submissions = submissionService.getUserSubmissions(username, status, pageable);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            log.error("Error getting user submissions", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/problem/{problemId}")
    public ResponseEntity<Page<SubmissionDTO>> getProblemSubmissions(
            @PathVariable Long problemId,
            Pageable pageable) {
        try {
            Page<SubmissionDTO> submissions = submissionService.getProblemSubmissions(problemId, pageable);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            log.error("Error getting problem submissions", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SubmissionDTO>> getSessionSubmissions(@PathVariable String sessionId) {
        try {
            List<SubmissionDTO> submissions = submissionService.getSessionSubmissions(sessionId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            log.error("Error getting session submissions", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserSubmissionStats(Authentication authentication) {
        try {
            String username = authentication.getName();
            Map<String, Object> stats = submissionService.getUserSubmissionStats(username);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Error getting submission stats", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
