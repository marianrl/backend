package com.ams.backend.repository;

import com.ams.backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query(value = "SELECT a FROM Answer a WHERE a.id IN " +
            "(SELECT f.answer.id FROM Features f WHERE f.auditType.id = :auditTypeId)", countQuery = "SELECT COUNT(a) FROM Answer a WHERE a.id IN "
                    +
                    "(SELECT f.answer.id FROM Features f WHERE f.auditType.id = :auditTypeId)")
    List<Answer> findByAuditTypeId(@Param("auditTypeId") int auditTypeId);

}
