package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AuditMapper;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.AuditedRepository;
import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.response.AuditedResponse;
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

    @Mock
    private AuditedRepository auditedRepository;

    @Mock
    private AuditMapper auditMapper;

    private AuditServiceImpl auditService;

    private Audit audit;
    private AuditType auditType;
    private AuditResponse auditResponse;
    private AuditRequest auditRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditService = new AuditServiceImpl(auditRepository, auditTypeRepository, auditedRepository, auditMapper);

        // Create sample entities and DTOs
        auditType = new AuditType();
        auditType.setId(1);
        auditType.setAuditType("Financial Audit");

        audit = new Audit();
        audit.setId(1);
        audit.setAuditDate(LocalDate.now());
        audit.setIdTipoAuditoria(auditType);
        audit.setIdAuditado(new Audited(2, "No"));

        AuditTypeResponse auditTypeResponse = new AuditTypeResponse(1, "Financial Audit");
        AuditedResponse auditedResponse = new AuditedResponse(2, "No");
        auditResponse = new AuditResponse(1, LocalDate.now(), auditTypeResponse, auditedResponse);
        auditRequest = new AuditRequest(1);
    }

    @Test
    public void testGetAllAudit() {
        List<Audit> audits = Arrays.asList(audit);
        when(auditRepository.findAll()).thenReturn(audits);
        when(auditMapper.toResponse(audit)).thenReturn(auditResponse);

        List<AuditResponse> result = auditService.getAllAudit();

        assertEquals(1, result.size());
        assertEquals(auditResponse, result.get(0));
        verify(auditRepository, times(1)).findAll();
        verify(auditMapper, times(1)).toResponse(audit);
    }

    @Test
    public void testGetAuditById_AuditFound() throws ResourceNotFoundException {
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(auditMapper.toResponse(audit)).thenReturn(auditResponse);

        AuditResponse result = auditService.getAuditById(1);

        assertNotNull(result);
        assertEquals(auditResponse, result);
        verify(auditRepository, times(1)).findById(1);
        verify(auditMapper, times(1)).toResponse(audit);
    }

    @Test
    public void testGetAuditById_AuditNotFound() {
        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.getAuditById(999));

        verify(auditRepository, times(1)).findById(999);
        verify(auditMapper, times(0)).toResponse(any());
    }

    @Test
    public void testCreateAudit_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(auditMapper.toEntity(auditRequest)).thenReturn(audit);
        when(auditRepository.save(audit)).thenReturn(audit);
        when(auditMapper.toResponse(audit)).thenReturn(auditResponse);

        AuditResponse result = auditService.createAudit(auditRequest);

        assertNotNull(result);
        assertEquals(auditResponse, result);
        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditMapper, times(1)).toEntity(auditRequest);
        verify(auditRepository, times(1)).save(audit);
        verify(auditMapper, times(1)).toResponse(audit);
    }

    @Test
    public void testCreateAudit_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.createAudit(new AuditRequest(999)));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateAudit_AuditFound() throws ResourceNotFoundException {
        Audit updatedAudit = new Audit();
        updatedAudit.setId(1);
        updatedAudit.setAuditDate(LocalDate.now().plusDays(1));
        updatedAudit.setIdTipoAuditoria(auditType);
        updatedAudit.setIdAuditado(new Audited(2, "No"));

        AuditResponse updatedResponse = new AuditResponse(
                1,
                LocalDate.now().plusDays(1),
                new AuditTypeResponse(1, "Financial Audit"),
                new AuditedResponse(2, "No"));

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(auditedRepository.findById(1)).thenReturn(Optional.of(new Audited(1, "Yes")));
        when(auditRepository.save(any(Audit.class))).thenReturn(updatedAudit);
        when(auditMapper.toResponse(updatedAudit)).thenReturn(updatedResponse);

        AuditResponse result = auditService.updateAudit(1, auditRequest);

        assertNotNull(result);
        assertEquals(updatedResponse, result);
        verify(auditRepository, times(1)).findById(1);
        verify(auditedRepository, times(1)).findById(1);
        verify(auditRepository, times(1)).save(any(Audit.class));
        verify(auditMapper, times(1)).toResponse(updatedAudit);
    }

    @Test
    public void testUpdateAudit_AuditNotFound() {
        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditService.updateAudit(999, auditRequest));

        verify(auditRepository, times(1)).findById(999);
        verify(auditRepository, times(0)).save(any());
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
        verify(auditRepository, times(0)).deleteById(999);
    }
}
