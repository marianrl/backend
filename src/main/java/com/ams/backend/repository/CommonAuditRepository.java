package com.ams.backend.repository;

import com.ams.backend.entity.CommonAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonAuditRepository extends JpaRepository<CommonAudit, Integer> {

}
