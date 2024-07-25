package com.ams.backend.repository;

import com.ams.backend.entity.AfipInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AfipInputRepository extends JpaRepository<AfipInput, Integer>, JpaSpecificationExecutor<AfipInput> {

    List<AfipInput> findByAudit_Id(int id);
}
