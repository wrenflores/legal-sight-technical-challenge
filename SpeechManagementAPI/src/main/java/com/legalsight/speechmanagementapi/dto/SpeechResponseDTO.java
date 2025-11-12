package com.legalsight.speechmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeechResponseDTO {

    private long id;

    private String author;

    private String subject;

    private String body;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate speechDate;
}