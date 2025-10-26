package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_cases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(name = "input_data", columnDefinition = "TEXT", nullable = false)
    private String inputData;
    
    @Column(name = "expected_output", columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;
    
    @Column(name = "is_public")
    @Builder.Default
    private Boolean isPublic = false;
    
    @Column(name = "execution_time_limit")
    private Long executionTimeLimit; // in milliseconds
    
    @Column(name = "memory_limit")
    private Long memoryLimit; // in bytes
    
    @Column(name = "test_case_order")
    private Integer testCaseOrder;
    
    @Column(name = "description")
    private String description;
}
