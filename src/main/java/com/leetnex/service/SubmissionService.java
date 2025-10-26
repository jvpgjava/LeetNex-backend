package com.leetnex.service;

import com.leetnex.dto.SubmissionDTO;
import com.leetnex.mapper.UserMapper;
import com.leetnex.model.*;
import com.leetnex.repository.SubmissionRepository;
import com.leetnex.repository.UserRepository;
import com.leetnex.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubmissionService {
    
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final CodeExecutionService codeExecutionService;
    
    public SubmissionDTO submitCode(String username, Long problemId, String code, 
                                  Submission.ProgrammingLanguage language, String sessionId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found"));
        
        Submission submission = Submission.builder()
            .user(user)
            .problem(problem)
            .code(code)
            .language(language)
            .status(Submission.SubmissionStatus.PENDING)
            .submittedAt(LocalDateTime.now())
            .sessionId(sessionId)
            .build();
        
        submission = submissionRepository.save(submission);
        
        // Execute code asynchronously
        executeCodeAsync(submission, problem);
        
        return mapToDTO(submission);
    }
    
    private void executeCodeAsync(Submission submission, Problem problem) {
        // This would typically be done in a separate thread or queue
        try {
            submission.setStatus(Submission.SubmissionStatus.RUNNING);
            submission = submissionRepository.save(submission);
            
            // Get test cases for the problem
            List<TestCase> testCases = problem.getTestCases().stream()
                .filter(TestCase::getIsPublic)
                .collect(Collectors.toList());
            
            if (testCases.isEmpty()) {
                submission.setStatus(Submission.SubmissionStatus.RUNTIME_ERROR);
                submission.setRuntimeError("No test cases available");
            } else {
                // Execute against first test case for now
                TestCase testCase = testCases.get(0);
                Submission.SubmissionStatus result = codeExecutionService.executeCode(
                    submission.getCode(), 
                    submission.getLanguage(), 
                    testCase.getInputData(), 
                    testCase.getExpectedOutput()
                );
                
                submission.setStatus(result);
                submission.setIsAccepted(result == Submission.SubmissionStatus.ACCEPTED);
                submission.setTestCasesPassed(result == Submission.SubmissionStatus.ACCEPTED ? 1 : 0);
                submission.setTotalTestCases(1);
            }
            
            submissionRepository.save(submission);
            
        } catch (Exception e) {
            log.error("Error executing code", e);
            submission.setStatus(Submission.SubmissionStatus.RUNTIME_ERROR);
            submission.setRuntimeError(e.getMessage());
            submissionRepository.save(submission);
        }
    }
    
    public Page<SubmissionDTO> getUserSubmissions(String username, Submission.SubmissionStatus status, Pageable pageable) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Page<Submission> submissions;
        if (status != null) {
            submissions = submissionRepository.findByUserAndStatus(user, status, pageable);
        } else {
            submissions = submissionRepository.findByUserOrderBySubmittedAtDesc(user, pageable);
        }
        
        return submissions.map(this::mapToDTO);
    }
    
    public Page<SubmissionDTO> getProblemSubmissions(Long problemId, Pageable pageable) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found"));
        
        Page<Submission> submissions = submissionRepository.findByProblemOrderBySubmittedAtDesc(problem, pageable);
        return submissions.map(this::mapToDTO);
    }
    
    public List<SubmissionDTO> getSessionSubmissions(String sessionId) {
        List<Submission> submissions = submissionRepository.findBySessionId(sessionId);
        return submissions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    public Map<String, Object> getUserSubmissionStats(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        long totalSubmissions = submissionRepository.countByUser(user);
        long acceptedSubmissions = submissionRepository.countAcceptedSubmissionsByUser(user);
        double successRate = totalSubmissions > 0 ? (double) acceptedSubmissions / totalSubmissions * 100 : 0.0;
        
        List<Object[]> languageStats = submissionRepository.countSubmissionsByLanguage(user);
        Map<String, Long> languageCounts = languageStats.stream()
            .collect(Collectors.toMap(
                arr -> arr[0].toString(),
                arr -> (Long) arr[1]
            ));
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSubmissions", totalSubmissions);
        stats.put("acceptedSubmissions", acceptedSubmissions);
        stats.put("successRate", successRate);
        stats.put("languageCounts", languageCounts);
        
        return stats;
    }
    
    private SubmissionDTO mapToDTO(Submission submission) {
        return SubmissionDTO.builder()
            .id(submission.getId())
            .userId(submission.getUser().getId())
            .username(submission.getUser().getUsername())
            .problemId(submission.getProblem().getId())
            .problemTitle(submission.getProblem().getTitle())
            .code(submission.getCode())
            .language(submission.getLanguage())
            .status(submission.getStatus())
            .executionTime(submission.getExecutionTime())
            .memoryUsage(submission.getMemoryUsage())
            .runtimeError(submission.getRuntimeError())
            .compileError(submission.getCompileError())
            .testCasesPassed(submission.getTestCasesPassed())
            .totalTestCases(submission.getTotalTestCases())
            .submittedAt(submission.getSubmittedAt())
            .isAccepted(submission.getIsAccepted())
            .sessionId(submission.getSessionId())
            .build();
    }
}
