package com.ams.backend.service;

import com.ams.backend.entity.Audit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditRepository;
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
public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    private AuditService auditService;

    @Before
    public void setup() {
        auditService = new AuditService(auditRepository);
    }

    @Test
    public void testGetAllAudits() {
        List<Audit> audits = new ArrayList<>();
        Mockito.when(auditRepository.findAll()).thenReturn(audits);
        List<Audit> actualAudits = auditService.getAllAudit();

        assertEquals(audits, actualAudits);
    }

    //TODO
//    @Test
//    public void testGetAuditsById() throws ResourceNotFoundException {
//        int auditsId = 1;
//        Audit expectedAudit = new Audit(auditsId, "CABA");
//
//        Mockito.when(auditRepository.findById(auditsId)).thenReturn(Optional.of(expectedAudit));
//        Audit actualAudit = auditService.getAuditById(auditsId);
//
//        assertEquals(expectedAudit, actualAudit);
//    }
//
//    @Test
//    public void testCreateAudit() {
//        int auditId = 1;
//        Audit audit = new Audit(auditId, "CABA");
//
//        Mockito.when(auditRepository.save(audit)).thenReturn(audit);
//        Audit actualAudit = auditService.createAudit(audit);
//
//        assertEquals(actualAudit, audit);
//    }
//
//    @Test
//    public void testUpdateAudit() throws ResourceNotFoundException {
//        int auditId = 1;
//        Audit audit = new Audit(auditId, "CABA");
//        Audit updatedAudit = new Audit(auditId, "GBA");
//
//        Mockito.when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
//        Audit actualAudit = auditService.updateAudit(auditId, updatedAudit);
//
//        assertEquals(updatedAudit.getId(), actualAudit.getId());
//        assertEquals(updatedAudit.getAudit(), actualAudit.getAudit());
//    }
//
//    @Test
//    public void testDeleteAudit() throws ResourceNotFoundException {
//        int auditId = 1;
//        Audit audit = new Audit(auditId, "CABA");
//
//        Mockito.when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
//        auditService.deleteAudit(auditId);
//
//        verify(auditRepository).deleteById(1);
//    }
}

