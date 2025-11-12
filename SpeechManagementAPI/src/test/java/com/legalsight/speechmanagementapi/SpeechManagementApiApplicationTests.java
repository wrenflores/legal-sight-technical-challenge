package com.legalsight.speechmanagementapi;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import com.legalsight.speechmanagementapi.service.SpeechService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpeechManagementApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpeechService speechService;

    @Test
    void contextLoads() {
        // basic sanity check
    }

    @Test
    void testGetAllSpeeches() throws Exception {
        SpeechResponseDTO dto = new SpeechResponseDTO(1L, "John Doe", "Freedom", "Speech body", null);
        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(speechService.fetchAllSpeeches(any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(dto)));

        mockMvc.perform(get("/api/speeches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].author").value("John Doe"))
                .andExpect(jsonPath("$.content[0].subject").value("Freedom"));
    }

    @Test
    void testCreateSpeech() throws Exception {
        SpeechRequestDTO request = new SpeechRequestDTO("John Doe", "Freedom", "Speech body", null);
        SpeechResponseDTO response = new SpeechResponseDTO(1L, "John Doe", "Freedom", "Speech body", null);

        Mockito.when(speechService.createSpeech(any(SpeechRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/speech")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "author": "John Doe",
                                    "subject": "Freedom",
                                    "body": "Speech body"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.subject").value("Freedom"));
    }

    @Test
    void testUpdateSpeech() throws Exception {
        SpeechRequestDTO request = new SpeechRequestDTO("Jane Doe", "Peace", "Updated body", null);
        SpeechResponseDTO response = new SpeechResponseDTO(1L, "Jane Doe", "Peace", "Updated body", null);

        Mockito.when(speechService.updateSpeech(eq(1L), any(SpeechRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/speech/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "author": "Jane Doe",
                                    "subject": "Peace",
                                    "body": "Updated body"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Jane Doe"))
                .andExpect(jsonPath("$.subject").value("Peace"));
    }

    @Test
    void testDeleteSpeech() throws Exception {
        Mockito.doNothing().when(speechService).deleteSpeech(1L);

        mockMvc.perform(delete("/api/speech/1"))
                .andExpect(status().isNoContent());
    }

}
