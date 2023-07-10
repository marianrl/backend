package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Audited;
import com.ams.backend.repository.AuditedRepository;
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
public class AuditedServiceTest {

    @Mock
    private AuditedRepository auditedRepository;

    private AuditedService auditedService;

    @Before
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
        Long auditedId = 1L;
        Audited expectedAudited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.findById(auditedId)).thenReturn(Optional.of(expectedAudited));
        Audited actualAudited = auditedService.getAuditedById(auditedId);

        assertEquals(expectedAudited, actualAudited);
    }

    @Test
    public void testCreateAudited() {
        Long auditedId = 1L;
        Audited audited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.save(audited)).thenReturn(audited);
        Audited actualAudited = auditedService.createAudited(audited);

        assertEquals(actualAudited, audited);
    }

    @Test
    public void testUpdateAudited() throws ResourceNotFoundException {
        Long auditedId = 1L;
        Audited audited = new Audited(auditedId, "CABA");
        Audited updatedAudited = new Audited(auditedId, "GBA");

        Mockito.when(auditedRepository.findById(1L)).thenReturn(Optional.of(audited));
        Audited actualAudited = auditedService.updateAudited(auditedId, updatedAudited);

        assertEquals(updatedAudited.getId(), actualAudited.getId());
        assertEquals(updatedAudited.getAudited(), actualAudited.getAudited());
    }

    @Test
    public void testDeleteAudited() throws ResourceNotFoundException {
        Long auditedId = 1L;
        Audited audited = new Audited(auditedId, "CABA");

        Mockito.when(auditedRepository.findById(1L)).thenReturn(Optional.of(audited));
        auditedService.deleteAudited(auditedId);

        verify(auditedRepository).deleteById(1L);
    }
}

