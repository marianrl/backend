package com.ams.backend.repository;

import com.ams.backend.entity.AuditType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTypeRepository extends JpaRepository<AuditType, Long> {

}
