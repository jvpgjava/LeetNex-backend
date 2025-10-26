package com.leetnex.service;

import com.leetnex.dto.UserDTO;
import com.leetnex.mapper.UserMapper;
import com.leetnex.model.User;
import com.leetnex.repository.UserRepository;
import com.leetnex.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }
    
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = User.builder()
            .username(userDTO.getUsername())
            .email(userDTO.getEmail())
            .password(passwordEncoder.encode(userDTO.getPassword()))
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .role(User.Role.USER)
            .isEnabled(true)
            .isAccountNonLocked(true)
            .createdAt(LocalDateTime.now())
            .build();
        
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }
    
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());
        user.setLeetcodeUsername(userDTO.getLeetcodeUsername());
        user.setGithubUsername(userDTO.getGithubUsername());
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        
        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDTO(updatedUser);
    }
    
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
        
        // Calculate statistics
        long totalSubmissions = submissionRepository.countByUser(user);
        long acceptedSubmissions = submissionRepository.countAcceptedSubmissionsByUser(user);
        double successRate = totalSubmissions > 0 ? (double) acceptedSubmissions / totalSubmissions * 100 : 0.0;
        
        userDTO.setTotalSubmissions(totalSubmissions);
        userDTO.setAcceptedSubmissions(acceptedSubmissions);
        userDTO.setSuccessRate(successRate);
        
        return userDTO;
    }
    
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.INSTANCE.toDTO(user);
    }
    
    public void updateLastLogin(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
