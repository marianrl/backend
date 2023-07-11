package com.ams.backend.service;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuditTypeServiceTest {

    @Mock
    private AuditTypeRepository auditTypeRepository;

    private AuditTypeService auditTypeService;

    @Before
    public void setup() {
        auditTypeService = new AuditTypeService(auditTypeRepository);
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
        Long auditTypeId = 1L;
        AuditType expectedAuditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.findById(auditTypeId)).thenReturn(Optional.of(expectedAuditType));
        AuditType actualAuditType = auditTypeService.getAuditTypeById(auditTypeId);

        assertEquals(expectedAuditType, actualAuditType);
    }

    @Test
    public void testCreateAuditType() {
        Long auditTypeId = 1L;
        AuditType auditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.save(auditType)).thenReturn(auditType);
        AuditType actualAuditType = auditTypeService.createAuditType(auditType);

        assertEquals(actualAuditType, auditType);
    }

    @Test
    public void testUpdateAuditType() throws ResourceNotFoundException {
        Long auditTypeId = 1L;
        AuditType auditType = new AuditType(auditTypeId, "CABA");
        AuditType updatedAuditType = new AuditType(auditTypeId, "GBA");

        Mockito.when(auditTypeRepository.findById(1L)).thenReturn(Optional.of(auditType));
        AuditType actualAuditType = auditTypeService.updateAuditType(auditTypeId, updatedAuditType);

        assertEquals(updatedAuditType.getId(), actualAuditType.getId());
        assertEquals(updatedAuditType.getAuditType(), actualAuditType.getAuditType());
    }

    @Test
    public void testDeleteAnswer() throws ResourceNotFoundException {
        Long auditTypeId = 1L;
        AuditType auditType = new AuditType(auditTypeId, "CABA");

        Mockito.when(auditTypeRepository.findById(1L)).thenReturn(Optional.of(auditType));
        auditTypeService.deleteAuditType(auditTypeId);

        verify(auditTypeRepository).deleteById(1L);
    }
}

