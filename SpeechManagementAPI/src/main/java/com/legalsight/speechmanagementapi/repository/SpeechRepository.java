package com.legalsight.speechmanagementapi.repository;

import com.legalsight.speechmanagementapi.entity.SpeechEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpeechRepository extends JpaRepository<SpeechEntity, Long>, JpaSpecificationExecutor<SpeechEntity> {
}
