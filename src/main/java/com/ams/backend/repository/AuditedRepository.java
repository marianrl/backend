package com.ams.backend.repository;

import com.ams.backend.entity.Audited;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditedRepository extends JpaRepository<Audited, Long> {

}
