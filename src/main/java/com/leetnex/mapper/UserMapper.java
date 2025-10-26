package com.leetnex.mapper;

import com.leetnex.dto.UserDTO;
import com.leetnex.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    @Mapping(target = "totalSubmissions", ignore = true)
    @Mapping(target = "acceptedSubmissions", ignore = true)
    @Mapping(target = "successRate", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    UserDTO toDTO(User user);
    
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "practiceSessions", ignore = true)
    @Mapping(target = "userProgress", ignore = true)
    User toEntity(UserDTO userDTO);
}
