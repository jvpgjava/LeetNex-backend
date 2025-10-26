package com.leetnex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problem_templates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTemplate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProgrammingLanguage language;
    
    @Column(name = "template_code", columnDefinition = "TEXT")
    private String templateCode;
    
    @Column(name = "function_signature", columnDefinition = "TEXT")
    private String functionSignature;
    
    @Column(name = "imports", columnDefinition = "TEXT")
    private String imports;
    
    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;
    
    public enum ProgrammingLanguage {
        JAVA, PYTHON, JAVASCRIPT, CPP, C, CSHARP, GO, RUST, SWIFT, KOTLIN
    }
}
