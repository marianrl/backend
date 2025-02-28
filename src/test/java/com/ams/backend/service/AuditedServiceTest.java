package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AuditedServiceTest {

    @Mock
    private AuditedRepository auditedRepository;

    private AuditedServiceImpl auditedService;
    private Audited audited;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditedService = new AuditedServiceImpl(auditedRepository);

        // Crear un auditado de ejemplo
        audited = new Audited(1, "AuditedName");
    }

    @Test
    public void testGetAllAudited() {
        List<Audited> auditedList = Arrays.asList(audited);

        when(auditedRepository.findAll()).thenReturn(auditedList);

        List<Audited> result = auditedService.getAllAudited();

        assertEquals(1, result.size());
        assertEquals(audited.getId(), result.get(0).getId());
        verify(auditedRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuditedById_AuditedFound() throws ResourceNotFoundException {
        when(auditedRepository.findById(1)).thenReturn(Optional.of(audited));

        Audited result = auditedService.getAuditedById(1);

        assertNotNull(result);
        assertEquals(audited.getId(), result.getId());
        verify(auditedRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAuditedById_AuditedNotFound() {
        when(auditedRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditedService.getAuditedById(999));

        verify(auditedRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateAudited() {
        when(auditedRepository.save(any(Audited.class))).thenReturn(audited);

        Audited result = auditedService.createAudited(audited);

        assertNotNull(result);
        assertEquals(audited.getId(), result.getId());
        assertEquals(audited.getAudited(), result.getAudited());
        verify(auditedRepository, times(1)).save(audited);
    }

    @Test
    public void testUpdateAudited_AuditedFound() throws ResourceNotFoundException {
        Audited updatedAudited = new Audited(1, "UpdatedName");

        when(auditedRepository.findById(1)).thenReturn(Optional.of(audited));
        when(auditedRepository.save(any(Audited.class))).thenReturn(updatedAudited);

        Audited result = auditedService.updateAudited(1, updatedAudited);

        assertNotNull(result);
        assertEquals(updatedAudited.getAudited(), result.getAudited());
        verify(auditedRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateAudited_AuditedNotFound() {
        Audited updatedAudited = new Audited(1, "UpdatedName");

        when(auditedRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditedService.updateAudited(999, updatedAudited));

        verify(auditedRepository, times(1)).findById(999);
        verify(auditedRepository, times(0)).save(updatedAudited);  // No debe llamar a save
    }

    @Test
    public void testDeleteAudited_AuditedFound() throws ResourceNotFoundException {
        when(auditedRepository.findById(1)).thenReturn(Optional.of(audited));
        doNothing().when(auditedRepository).deleteById(1);

        auditedService.deleteAudited(1);

        verify(auditedRepository, times(1)).findById(1);
        verify(auditedRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAudited_AuditedNotFound() {
        when(auditedRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditedService.deleteAudited(999));

        verify(auditedRepository, times(1)).findById(999);
        verify(auditedRepository, times(0)).deleteById(999);  // No debe eliminarse
    }
}
