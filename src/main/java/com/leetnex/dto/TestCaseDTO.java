package com.leetnex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDTO {
    private Long id;
    private String inputData;
    private String expectedOutput;
    private Boolean isPublic;
    private Long executionTimeLimit;
    private Long memoryLimit;
    private Integer testCaseOrder;
    private String description;
}
