package com.legalsight.speechmanagementapi.mapper;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import com.legalsight.speechmanagementapi.entity.SpeechEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpeechMapper {

    SpeechEntity toEntity(SpeechRequestDTO speechRequestDTO);

    SpeechRequestDTO toRequestDTO(SpeechEntity speechEntity);

    SpeechResponseDTO toResponseDTO(SpeechEntity speechEntity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(SpeechRequestDTO dto, @MappingTarget SpeechEntity entity);

}
