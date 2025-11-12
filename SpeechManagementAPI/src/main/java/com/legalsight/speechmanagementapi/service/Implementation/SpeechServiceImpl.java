package com.legalsight.speechmanagementapi.service.Implementation;

import com.legalsight.speechmanagementapi.dto.SpeechRequestDTO;
import com.legalsight.speechmanagementapi.dto.SpeechResponseDTO;
import com.legalsight.speechmanagementapi.entity.SpeechEntity;
import com.legalsight.speechmanagementapi.exception.ErrorContants;
import com.legalsight.speechmanagementapi.exception.GenericException;
import com.legalsight.speechmanagementapi.mapper.SpeechMapper;
import com.legalsight.speechmanagementapi.repository.SpeechRepository;
import com.legalsight.speechmanagementapi.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SpeechServiceImpl implements SpeechService {

    @Autowired
    private SpeechRepository speechRepository;

    @Autowired
    private SpeechMapper speechMapper;


    @Override
    public Page<SpeechResponseDTO> fetchAllSpeeches(
            String author,
            String subject,
            String body,
            String startDate,
            String endDate,
            Pageable pageable) {


        Specification<SpeechEntity> spec = null;

        if (author != null && !author.isEmpty()) {
            Specification<SpeechEntity> authorSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
            spec = (spec == null ? authorSpec : spec.and(authorSpec));
        }

        if (subject != null && !subject.isEmpty()) {
            Specification<SpeechEntity> subjectSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("subject")), "%" + subject.toLowerCase() + "%");
            spec = (spec == null ? subjectSpec : spec.and(subjectSpec));
        }

        if (body != null && !body.isEmpty()) {
            Specification<SpeechEntity> textSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("body")), "%" + body.toLowerCase() + "%");
            spec = (spec == null ? textSpec : spec.and(textSpec));
        }

        if (startDate != null && !startDate.isEmpty()) {
            LocalDate start = LocalDate.parse(startDate);
            Specification<SpeechEntity> startSpec = (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("speechDate"), start);
            spec = (spec == null ? startSpec : spec.and(startSpec));
        }

        if (endDate != null && !endDate.isEmpty()) {
            LocalDate end = LocalDate.parse(endDate);
            Specification<SpeechEntity> endSpec = (root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("speechDate"), end);
            spec = (spec == null ? endSpec : spec.and(endSpec));
        }

        Page<SpeechEntity> speeches = (spec == null)
                ? speechRepository.findAll(pageable) // no filters
                : speechRepository.findAll(spec, pageable);

        return speeches.map(speechMapper::toResponseDTO);
    }

    @Override
    public SpeechResponseDTO createSpeech(SpeechRequestDTO speechRequestDTO) {
        return speechMapper.toResponseDTO(speechRepository.save(speechMapper.toEntity(speechRequestDTO)));
    }

    @Override
    public SpeechResponseDTO updateSpeech(long id, SpeechRequestDTO speechRequestDTO) {
        SpeechEntity speechEntity = speechRepository.findById(id)
                .orElseThrow(
                        () -> new GenericException("SPH001", ErrorContants.getMessage("SPH001"))
                );

        speechMapper.updateEntityFromDto(speechRequestDTO,speechEntity);

        return speechMapper.toResponseDTO(speechRepository.save(speechEntity));
    }

    @Override
    public void deleteSpeech(long id) {
        SpeechEntity speechEntity = speechRepository.findById(id)
                .orElseThrow(() -> new GenericException("SPH001", ErrorContants.getMessage("SPH001")));

        speechRepository.delete(speechEntity);
    }
}
