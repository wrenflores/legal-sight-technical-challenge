package com.legalsight.speechmanagementapi.controller;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import com.legalsight.speechmanagementapi.exception.ExceptionResponse;
import com.legalsight.speechmanagementapi.service.SpeechService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Speech Management", description = "API for managing speeches")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @GetMapping("/speeches")
    @Operation(summary = "Get all speeches", description = "Fetches all speeches with optional filters")
    public ResponseEntity<Page<SpeechResponseDTO>> getAllSpeeches(
            @Parameter(description = "Author name") @RequestParam(required = false) String author,
            @Parameter(description = "Speech subject")@RequestParam(required = false) String subject,
            @Parameter(description = "Body text")@RequestParam(required = false) String body,
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam(required = false) String startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam(required = false) String endDate,
            Pageable pageable
    ){

        return ResponseEntity.ok(speechService.fetchAllSpeeches(author,subject,body,startDate,endDate,pageable));
    }

    @PostMapping("/speech")
    @Operation(summary = "Create a speech", description = "Creates a new speech")
    @ApiResponse(responseCode = "200", description = "Speech created successfully",
            content = @Content(schema = @Schema(implementation = SpeechResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<SpeechResponseDTO> postSpeech(
            @Parameter(description = "Speech data") @Valid @RequestBody SpeechRequestDTO speechRequestDTO
    ){
        return ResponseEntity.ok(speechService.createSpeech(speechRequestDTO));
    }

    @PutMapping("/speech/{id}")
    @Operation(summary = "Update a speech", description = "Updates an existing speech by ID")
    @ApiResponse(responseCode = "200", description = "Speech updated successfully",
            content = @Content(schema = @Schema(implementation = SpeechResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<SpeechResponseDTO> putSpeech(
            @Parameter(description = "Speech ID") @PathVariable long id,
            @Parameter(description = "Updated speech data") @Valid @RequestBody SpeechRequestDTO speechRequestDTO
    ){
        return ResponseEntity.ok(speechService.updateSpeech(id, speechRequestDTO));
    }

    @DeleteMapping("/speech/{id}")
    @Operation(summary = "Delete a speech", description = "Deletes a speech by ID")
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    public ResponseEntity<Void> deleteSpeech(
            @Parameter(description = "Updated speech data") @PathVariable long id
    ) {
        speechService.deleteSpeech(id);
        return ResponseEntity.noContent().build();
    }



}
