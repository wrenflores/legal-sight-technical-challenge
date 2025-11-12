package com.legalsight.speechmanagementapi.service;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SpeechService {

    Page<SpeechResponseDTO> fetchAllSpeeches(
            String author,
            String subject,
            String body,
            String startDate,
            String endDate,
            Pageable pageable
    );

    SpeechResponseDTO createSpeech(SpeechRequestDTO speechRequestDTO);

    SpeechResponseDTO updateSpeech(long id, @Valid SpeechRequestDTO speechRequestDTO);

    void deleteSpeech(long id);
}
