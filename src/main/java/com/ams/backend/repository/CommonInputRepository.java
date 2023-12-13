package com.ams.backend.repository;

import com.ams.backend.entity.CommonInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommonInputRepository extends JpaRepository<CommonInput, Integer>, JpaSpecificationExecutor<CommonInput> {

    List<CommonInput> findByAudit_AuditNumber(int id);

}
