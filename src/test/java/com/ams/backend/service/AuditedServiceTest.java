package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Audited;
import com.ams.backend.repository.AuditedRepository;

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
public class AuditedServiceTest {

    @Mock
    private AuditedRepository auditedRepository;

    private AuditedService auditedService;

    @BeforeEach
    public void setup() {
        auditedService = new AuditedService(auditedRepository);
    }

    @Test
    public void testGetAllAudited() {
        List<Audited> audited = new ArrayList<>();
        Mockito.when(auditedRepository.findAll()).thenReturn(audited);
        List<Audited> actualAudited = auditedService.getAllAudited();

        assertEquals(audited, actualAudited);
    }

    @Test
    public void testGetAuditedById() throws ResourceNotFoundException {
        int auditedId = 1;
        Audited expectedAudited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.findById(auditedId)).thenReturn(Optional.of(expectedAudited));
        Audited actualAudited = auditedService.getAuditedById(auditedId);

        assertEquals(expectedAudited, actualAudited);
    }

    @Test
    public void testCreateAudited() {
        int auditedId = 1;
        Audited audited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.save(audited)).thenReturn(audited);
        Audited actualAudited = auditedService.createAudited(audited);

        assertEquals(actualAudited, audited);
    }

    @Test
    public void testUpdateAudited() throws ResourceNotFoundException {
        int auditedId = 1;
        Audited audited = new Audited(auditedId, "CABA");
        Audited updatedAudited = new Audited(auditedId, "GBA");

        Mockito.when(auditedRepository.findById(1)).thenReturn(Optional.of(audited));
        Audited actualAudited = auditedService.updateAudited(auditedId, updatedAudited);

        assertEquals(updatedAudited.getId(), actualAudited.getId());
        assertEquals(updatedAudited.getAudited(), actualAudited.getAudited());
    }

    @Test
    public void testDeleteAudited() throws ResourceNotFoundException {
        int auditedId = 1;
        Audited audited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.findById(1)).thenReturn(Optional.of(audited));
        auditedService.deleteAudited(auditedId);

        verify(auditedRepository).deleteById(1);
    }
}

