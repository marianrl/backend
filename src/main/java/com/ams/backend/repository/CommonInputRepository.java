package com.ams.backend.repository;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.CommonInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonInputRepository extends JpaRepository<CommonInput, Integer> {

    List<CommonInput> findByAuditNumber(int id);

}
