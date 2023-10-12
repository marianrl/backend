package com.ams.backend.repository;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Integer> {

}
