package com.ams.backend.service;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuditTypeServiceTest {

    @Mock
    private AuditTypeRepository auditTypeRepository;

    private AuditTypeServiceImpl auditTypeService;

    @BeforeEach
    public void setup() {
        auditTypeService = new AuditTypeServiceImpl(auditTypeRepository);
    }

    @Test
    public void testGetAllAuditTypes() {
        List<AuditType> auditTypes = new ArrayList<>();
        Mockito.when(auditTypeRepository.findAll()).thenReturn(auditTypes);
        List<AuditType> actualAuditTypes = auditTypeService.getAllAuditType();

        assertEquals(auditTypes, actualAuditTypes);
    }

    @Test
    public void testGetAuditTypeById() throws ResourceNotFoundException {
        int auditTypeId = 1;
        AuditType expectedAuditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.findById(auditTypeId)).thenReturn(Optional.of(expectedAuditType));
        AuditType actualAuditType = auditTypeService.getAuditTypeById(auditTypeId);

        assertEquals(expectedAuditType, actualAuditType);
    }

    @Test
    public void testCreateAuditType() {
        int auditTypeId = 1;
        AuditType auditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.save(auditType)).thenReturn(auditType);
        AuditType actualAuditType = auditTypeService.createAuditType(auditType);

        assertEquals(actualAuditType, auditType);
    }

    @Test
    public void testUpdateAuditType() throws ResourceNotFoundException {
        int auditTypeId = 1;
        AuditType auditType = new AuditType(auditTypeId, "CABA");
        AuditType updatedAuditType = new AuditType(auditTypeId, "GBA");

        Mockito.when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        AuditType actualAuditType = auditTypeService.updateAuditType(auditTypeId, updatedAuditType);

        assertEquals(updatedAuditType.getId(), actualAuditType.getId());
        assertEquals(updatedAuditType.getAuditType(), actualAuditType.getAuditType());
    }

    @Test
    public void testDeleteAnswer() throws ResourceNotFoundException {
        int auditTypeId = 1;
        AuditType auditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        auditTypeService.deleteAuditType(auditTypeId);

        verify(auditTypeRepository).deleteById(1);
    }
}

