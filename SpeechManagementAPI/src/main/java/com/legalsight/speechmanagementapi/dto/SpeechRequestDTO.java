package com.legalsight.speechmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeechRequestDTO {

    @NotBlank(message = "SPH002")
    private String author;

    @NotBlank(message = "SPH003")
    private String subject;

    @NotBlank(message = "SPH004")
    private String body;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate speechDate;
}