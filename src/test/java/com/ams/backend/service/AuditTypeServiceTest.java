package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AuditTypeServiceTest {

    @Mock
    private AuditTypeRepository auditTypeRepository;

    private AuditTypeServiceImpl auditTypeService;

    private AuditType auditType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditTypeService = new AuditTypeServiceImpl(auditTypeRepository);

        // Crear un tipo de auditoría de ejemplo
        auditType = new AuditType();
        auditType.setId(1);
        auditType.setAuditType("Financial Audit");
    }

    @Test
    public void testGetAllAuditType() {
        List<AuditType> auditTypes = Arrays.asList(auditType);

        when(auditTypeRepository.findAll()).thenReturn(auditTypes);

        List<AuditType> result = auditTypeService.getAllAuditType();

        assertEquals(1, result.size());
        assertEquals(auditType.getAuditType(), result.get(0).getAuditType());
        verify(auditTypeRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuditTypeById_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));

        AuditType result = auditTypeService.getAuditTypeById(1);

        assertNotNull(result);
        assertEquals(auditType.getAuditType(), result.getAuditType());
        verify(auditTypeRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAuditTypeById_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.getAuditTypeById(999));

        verify(auditTypeRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateAuditType() {
        AuditType newAuditType = new AuditType();
        newAuditType.setAuditType("Operational Audit");

        when(auditTypeRepository.save(newAuditType)).thenReturn(newAuditType);

        AuditType result = auditTypeService.createAuditType(newAuditType);

        assertNotNull(result);
        assertEquals("Operational Audit", result.getAuditType());
        verify(auditTypeRepository, times(1)).save(newAuditType);
    }

    @Test
    public void testUpdateAuditType_AuditTypeFound() throws ResourceNotFoundException {
        AuditType updatedAuditType = new AuditType();
        updatedAuditType.setAuditType("Updated Financial Audit");

        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(auditTypeRepository.save(any(AuditType.class))).thenReturn(updatedAuditType);

        AuditType result = auditTypeService.updateAuditType(1, updatedAuditType);

        assertNotNull(result);
        assertEquals("Updated Financial Audit", result.getAuditType());
        verify(auditTypeRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateAuditType_AuditTypeNotFound() {
        AuditType updatedAuditType = new AuditType();
        updatedAuditType.setAuditType("Updated Financial Audit");

        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.updateAuditType(999, updatedAuditType));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).save(updatedAuditType);  // No debe llamar a save
    }

    @Test
    public void testDeleteAuditType_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        doNothing().when(auditTypeRepository).deleteById(1);

        auditTypeService.deleteAuditType(1);

        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditTypeRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAuditType_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.deleteAuditType(999));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).deleteById(999);  // No debe eliminarse
    }
}
