package com.ams.backend.repository;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Features;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeaturesRepository extends JpaRepository<Features, Integer> {
    Features findByAuditTypeAndAnswer(AuditType auditType, Answer answer);
}
