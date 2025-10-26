package com.leetnex.dto;

import com.leetnex.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private User.Role role;
    private Boolean isEnabled;
    private Boolean isAccountNonLocked;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String profilePictureUrl;
    private String leetcodeUsername;
    private String githubUsername;
    private String bio;
    private Long totalSubmissions;
    private Long acceptedSubmissions;
    private Double successRate;
}
