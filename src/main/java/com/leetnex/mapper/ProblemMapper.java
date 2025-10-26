package com.leetnex.mapper;

import com.leetnex.dto.ProblemDTO;
import com.leetnex.model.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;

@Mapper
public interface ProblemMapper {
    ProblemMapper INSTANCE = Mappers.getMapper(ProblemMapper.class);

    @Mapping(target = "testCases", ignore = true)
    @Mapping(target = "templates", ignore = true)
    ProblemDTO toDTO(Problem problem);

    @Mapping(target = "testCases", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "problemTemplates", ignore = true)
    Problem toEntity(ProblemDTO problemDTO);

    default List<String> mapTags(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(tags.split(","));
    }

    default String mapTagsToString(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return String.join(",", tags);
    }
}
