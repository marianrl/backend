package com.ams.backend.service;

import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    private AuditServiceImpl auditService;

    private int auditId;
    private int auditTypeId;
    private Audit audit;
    private Audit updatedAudit;
    private AuditType auditType;
    private List<Audit> expectedAudits;

    @BeforeEach
    public void setup() {
        auditService = new AuditServiceImpl(auditRepository, auditTypeRepository);
        auditId = 1;
        auditTypeId = 1;
        auditType = new AuditType(auditTypeId, "Audit Type");
        audit = new Audit();
        audit.setId(auditId);
        audit.setAuditDate(LocalDate.now());
        audit.setIdTipoAuditoria(auditType);
        audit.setIdAuditado(new Audited(2, "No"));

        updatedAudit = new Audit();
        updatedAudit.setId(auditId);
        updatedAudit.setAuditDate(LocalDate.now().plusDays(1));
        updatedAudit.setIdTipoAuditoria(auditType);
        updatedAudit.setIdAuditado(new Audited(2, "Yes"));

        expectedAudits = new ArrayList<>();
        expectedAudits.add(audit);
    }

    @Test
    public void testGetAllAudit_Success() {
        Mockito.when(auditRepository.findAll()).thenReturn(expectedAudits);

        List<Audit> actualAudits = auditService.getAllAudit();

        assertEquals(expectedAudits, actualAudits);
        verify(auditRepository).findAll();
    }

    @Test
    public void testGetAuditById_Success() throws ResourceNotFoundException {
        Mockito.when(auditRepository.findById(auditId)).thenReturn(Optional.of(audit));

        Audit actualAudit = auditService.getAuditById(auditId);

        assertEquals(audit, actualAudit);
        verify(auditRepository).findById(auditId);
    }

    @Test
    public void testCreateAudit_Success() throws ResourceNotFoundException {
        Mockito.when(auditTypeRepository.findById(auditTypeId)).thenReturn(Optional.of(auditType));
        Mockito.when(auditRepository.save(Mockito.any(Audit.class))).thenReturn(audit);

        Audit actualAudit = auditService.createAudit(auditTypeId);

        assertEquals(audit.getAuditDate(), actualAudit.getAuditDate());
        assertEquals(audit.getIdTipoAuditoria(), actualAudit.getIdTipoAuditoria());
        verify(auditTypeRepository).findById(auditTypeId);
        verify(auditRepository).save(Mockito.any(Audit.class));
    }

    @Test
    public void testUpdateAudit_Success() throws ResourceNotFoundException {
        Mockito.when(auditRepository.findById(auditId)).thenReturn(Optional.of(audit));
        Mockito.when(auditRepository.save(audit)).thenReturn(updatedAudit);

        Audit actualAudit = auditService.updateAudit(auditId, updatedAudit);

        assertEquals(updatedAudit.getAuditDate(), actualAudit.getAuditDate());
        verify(auditRepository).findById(auditId);
        verify(auditRepository).save(audit);
    }

    @Test
    public void testDeleteAudit_Success() throws ResourceNotFoundException {
        Mockito.when(auditRepository.findById(auditId)).thenReturn(Optional.of(audit));

        auditService.deleteAudit(auditId);

        verify(auditRepository).findById(auditId);
        verify(auditRepository).deleteById(auditId);
    }
}
