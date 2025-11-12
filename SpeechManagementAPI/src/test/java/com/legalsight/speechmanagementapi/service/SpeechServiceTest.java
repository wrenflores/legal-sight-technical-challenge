package com.legalsight.speechmanagementapi.service;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import com.legalsight.speechmanagementapi.entity.SpeechEntity;
import com.legalsight.speechmanagementapi.exception.GenericException;
import com.legalsight.speechmanagementapi.mapper.SpeechMapper;
import com.legalsight.speechmanagementapi.repository.SpeechRepository;
import com.legalsight.speechmanagementapi.service.Implementation.SpeechServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpeechServiceTest {

    @Mock
    private SpeechRepository speechRepository;

    @Mock
    private SpeechMapper speechMapper;

    @InjectMocks
    private SpeechServiceImpl speechServiceImpl; // actual implementation

    private SpeechService speechService; // interface type

    private SpeechEntity speechEntity;
    private SpeechRequestDTO requestDTO;
    private SpeechResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        speechService = speechServiceImpl; // interface reference

        speechEntity = SpeechEntity.builder()
                .id(1L)
                .author("John Doe")
                .subject("AI Revolution")
                .body("Speech about AI")
                .speechDate(LocalDate.of(2025, 1, 1))
                .build();

        requestDTO = SpeechRequestDTO.builder()
                .author("John Doe")
                .subject("AI Revolution")
                .body("Speech about AI")
                .build();

        responseDTO = SpeechResponseDTO.builder()
                .id(1L)
                .author("John Doe")
                .subject("AI Revolution")
                .body("Speech about AI")
                .build();
    }

    @Test
    void fetchAllSpeeches_noFilters_returnsAllSpeeches() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SpeechEntity> entityPage = new PageImpl<>(List.of(speechEntity));

        when(speechRepository.findAll(pageable)).thenReturn(entityPage);
        when(speechMapper.toResponseDTO(speechEntity)).thenReturn(responseDTO);

        Page<SpeechResponseDTO> result =
                speechService.fetchAllSpeeches(null, null, null, null, null, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAuthor()).isEqualTo("John Doe");
        verify(speechRepository).findAll(pageable);
    }

    @Test
    void fetchAllSpeeches_withAuthorFilter_usesSpecification() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SpeechEntity> entityPage = new PageImpl<>(List.of(speechEntity));

        when(speechRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(entityPage);
        when(speechMapper.toResponseDTO(any(SpeechEntity.class))).thenReturn(responseDTO);

        Page<SpeechResponseDTO> result =
                speechService.fetchAllSpeeches("john", null, null, null, null, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(speechRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void fetchAllSpeeches_withDateRange_usesDateSpecifications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SpeechEntity> entityPage = new PageImpl<>(List.of(speechEntity));

        when(speechRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(entityPage);
        when(speechMapper.toResponseDTO(any(SpeechEntity.class))).thenReturn(responseDTO);

        Page<SpeechResponseDTO> result =
                speechService.fetchAllSpeeches(null, null, null, "2024-01-01", "2025-12-31", pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(speechRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void createSpeech_shouldSaveAndReturnResponse() {
        when(speechMapper.toEntity(requestDTO)).thenReturn(speechEntity);
        when(speechRepository.save(speechEntity)).thenReturn(speechEntity);
        when(speechMapper.toResponseDTO(speechEntity)).thenReturn(responseDTO);

        SpeechResponseDTO result = speechService.createSpeech(requestDTO);

        assertThat(result.getAuthor()).isEqualTo("John Doe");
        verify(speechRepository).save(any(SpeechEntity.class));
    }

    @Test
    void updateSpeech_shouldFindAndUpdateSuccessfully() {
        when(speechRepository.findById(1L)).thenReturn(Optional.of(speechEntity));
        doNothing().when(speechMapper).updateEntityFromDto(requestDTO, speechEntity);
        when(speechRepository.save(speechEntity)).thenReturn(speechEntity);
        when(speechMapper.toResponseDTO(speechEntity)).thenReturn(responseDTO);

        SpeechResponseDTO result = speechService.updateSpeech(1L, requestDTO);

        assertThat(result.getSubject()).isEqualTo("AI Revolution");
        verify(speechRepository).save(speechEntity);
    }

    @Test
    void updateSpeech_whenNotFound_shouldThrowException() {
        when(speechRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> speechService.updateSpeech(999L, requestDTO))
                .isInstanceOf(GenericException.class)
                .hasMessageContaining("Speech not found.");
    }

    @Test
    void deleteSpeech_shouldDeleteSuccessfully() {
        when(speechRepository.findById(1L)).thenReturn(Optional.of(speechEntity));

        speechService.deleteSpeech(1L);

        verify(speechRepository).delete(speechEntity);
    }

    @Test
    void deleteSpeech_whenNotFound_shouldThrowException() {
        when(speechRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> speechService.deleteSpeech(99L))
                .isInstanceOf(GenericException.class)
                .hasMessageContaining("Speech not found");
    }
}
