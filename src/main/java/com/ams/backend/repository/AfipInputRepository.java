package com.ams.backend.repository;

import com.ams.backend.entity.AfipInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AfipInputRepository extends JpaRepository<AfipInput, Integer> {

    List<AfipInput> findByAudit_AuditNumber(int id);
}
