package com.leetnex.dto;

import com.leetnex.model.Submission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long problemId;
    private String problemTitle;
    private String code;
    private Submission.ProgrammingLanguage language;
    private Submission.SubmissionStatus status;
    private Long executionTime;
    private Long memoryUsage;
    private String runtimeError;
    private String compileError;
    private Integer testCasesPassed;
    private Integer totalTestCases;
    private LocalDateTime submittedAt;
    private Boolean isAccepted;
    private String sessionId;
}
