package com.leetnex.dto;

import com.leetnex.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDTO {
    private Long id;
    private String title;
    private String description;
    private String examples;
    private String constraints;
    private Problem.Difficulty difficulty;
    private Problem.Category category;
    private Double acceptanceRate;
    private Long totalSubmissions;
    private Long acceptedSubmissions;
    private Boolean isPremium;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer leetcodeNumber;
    private List<String> tags;
    private List<TestCaseDTO> testCases;
    private List<ProblemTemplateDTO> templates;
}
