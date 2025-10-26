package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String examples;
    
    @Column(columnDefinition = "TEXT")
    private String constraints;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    
    @Column(name = "acceptance_rate")
    private Double acceptanceRate;
    
    @Column(name = "total_submissions")
    @Builder.Default
    private Long totalSubmissions = 0L;
    
    @Column(name = "accepted_submissions")
    @Builder.Default
    private Long acceptedSubmissions = 0L;
    
    @Column(name = "is_premium")
    @Builder.Default
    private Boolean isPremium = false;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "leetcode_number")
    private Integer leetcodeNumber;
    
    @Column(name = "tags")
    private String tags; // JSON string of tags
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestCase> testCases;
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Submission> submissions;
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProblemTemplate> problemTemplates;
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
    
    public enum Category {
        ARRAY, STRING, HASH_TABLE, DYNAMIC_PROGRAMMING, MATH, 
        SORTING, GREEDY, DEPTH_FIRST_SEARCH, BREADTH_FIRST_SEARCH,
        TREE, BINARY_SEARCH, MATRIX, TWO_POINTERS, BIT_MANIPULATION,
        STACK, QUEUE, LINKED_LIST, RECURSION, MEMOIZATION,
        TRIE, UNION_FIND, SEGMENT_TREE, BINARY_INDEXED_TREE
    }
}
