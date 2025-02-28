package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    private AuditServiceImpl auditService;

    private Audit audit;
    private AuditType auditType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditService = new AuditServiceImpl(auditRepository, auditTypeRepository);

        // Crear un tipo de auditoría y una auditoría de ejemplo
        auditType = new AuditType();
        auditType.setId(1);
        auditType.setAuditType("Financial Audit");

        audit = new Audit();
        audit.setId(1);
        audit.setAuditDate(LocalDate.now());
        audit.setIdTipoAuditoria(auditType);
        audit.setIdAuditado(new Audited(2, "No"));
    }

    @Test
    public void testGetAllAudit() {
        List<Audit> audits = Arrays.asList(audit);

        when(auditRepository.findAll()).thenReturn(audits);

        List<Audit> result = auditService.getAllAudit();

        assertEquals(1, result.size());
        assertEquals(audit.getId(), result.get(0).getId());
        verify(auditRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuditById_AuditFound() throws ResourceNotFoundException {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));

        Audit result = auditService.getAuditById(1);

        assertNotNull(result);
        assertEquals(audit.getId(), result.getId());
        verify(auditRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAuditById_AuditNotFound() {
        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.getAuditById(999));

        verify(auditRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateAudit_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(auditRepository.save(any(Audit.class))).thenReturn(audit);

        Audit result = auditService.createAudit(1);

        assertNotNull(result);
        assertEquals(audit.getId(), result.getId());
        assertEquals(auditType.getId(), result.getIdTipoAuditoria().getId());
        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditRepository, times(1)).save(any(Audit.class));
    }

    @Test
    public void testCreateAudit_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.createAudit(999));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditRepository, times(0)).save(any(Audit.class));  // No debe llamar a save
    }

    @Test
    public void testUpdateAudit_AuditFound() throws ResourceNotFoundException {
        Audit updatedAudit = new Audit();
        updatedAudit.setAuditDate(LocalDate.now().plusDays(1));

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(auditRepository.save(any(Audit.class))).thenReturn(updatedAudit);

        Audit result = auditService.updateAudit(1, updatedAudit);

        assertNotNull(result);
        assertEquals(updatedAudit.getAuditDate(), result.getAuditDate());
        verify(auditRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateAudit_AuditNotFound() {
        Audit updatedAudit = new Audit();
        updatedAudit.setAuditDate(LocalDate.now().plusDays(1));

        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.updateAudit(999, updatedAudit));

        verify(auditRepository, times(1)).findById(999);
        verify(auditRepository, times(0)).save(updatedAudit);  // No debe llamar a save
    }

    @Test
    public void testDeleteAudit_AuditFound() throws ResourceNotFoundException {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        doNothing().when(auditRepository).deleteById(1);

        auditService.deleteAudit(1);

        verify(auditRepository, times(1)).findById(1);
        verify(auditRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAudit_AuditNotFound() {
        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.deleteAudit(999));

        verify(auditRepository, times(1)).findById(999);
        verify(auditRepository, times(0)).deleteById(999);  // No debe eliminarse
    }
}
