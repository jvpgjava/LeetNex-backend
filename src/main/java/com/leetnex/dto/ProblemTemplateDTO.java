package com.leetnex.dto;

import com.leetnex.model.ProblemTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTemplateDTO {
    private Long id;
    private Long problemId;
    private ProblemTemplate.ProgrammingLanguage language;
    private String templateCode;
    private String functionSignature;
    private String imports;
    private Boolean isDefault;
}
