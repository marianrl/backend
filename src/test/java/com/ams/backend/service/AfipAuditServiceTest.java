package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.AfipAudit;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipAuditRepository;
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
public class AfipAuditServiceTest {

    @Mock
    private AfipAuditRepository afipAuditRepository;

    private AfipAuditService afipAuditService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer ,audited);
    final private AfipAudit afipAudit = new AfipAudit(
            1,
            "17/07/2023",
            "Perez",
            "Juan",
            "20-45125484-7",
            "4568",
            "1248",
            client,
            "Capital Federal",
            branch,
            "17/06/2023",
            features
    );

    @Before
    public void setup() {
        afipAuditService = new AfipAuditService(afipAuditRepository);
    }

    @Test
    public void testGetAllAfipAuditService() {
        List<AfipAudit> afipAudits = new ArrayList<>();
        Mockito.when(afipAuditRepository.findAll()).thenReturn(afipAudits);
        List<AfipAudit> actualAfipAudit = afipAuditService.getAllAfipAudits();

        assertEquals(afipAudits, actualAfipAudit);
    }

    @Test
    public void testGetAfipAuditById() throws ResourceNotFoundException {

        Mockito.when(afipAuditRepository.findById(afipAudit.getId())).thenReturn(Optional.of(afipAudit));
        AfipAudit actualAfipAudit = afipAuditService.getAfipAuditById(afipAudit.getId());

        assertEquals(afipAudit, actualAfipAudit);
    }

    @Test
    public void testCreateAfipAudit() {
        Mockito.when(afipAuditRepository.save(afipAudit)).thenReturn(afipAudit);
        AfipAudit actualAfipAudit = afipAuditService.createAfipAudit(afipAudit);

        assertEquals(afipAudit, actualAfipAudit);
    }

    @Test
    public void testUpdateAfipAudit() throws ResourceNotFoundException {
        Mockito.when(afipAuditRepository.findById(1)).thenReturn(Optional.of(afipAudit));

        AfipAudit actualAfipAudit = afipAuditService
                .updateAfipAudit(afipAudit.getId(), afipAudit);

        assertEquals(afipAudit.getAuditDate(), actualAfipAudit.getAuditDate());
        assertEquals(afipAudit.getLastName(), actualAfipAudit.getLastName());
        assertEquals(afipAudit.getName(), actualAfipAudit.getName());
        assertEquals(afipAudit.getCuil(), actualAfipAudit.getCuil());
        assertEquals(afipAudit.getFile(), actualAfipAudit.getFile());
        assertEquals(afipAudit.getAllocation(), actualAfipAudit.getAllocation());
        assertEquals(afipAudit.getClient().getClient(), actualAfipAudit.getClient().getClient());
        assertEquals(afipAudit.getUoc(), actualAfipAudit.getUoc());
        assertEquals(afipAudit.getBranch().getBranch(), actualAfipAudit.getBranch().getBranch());
        assertEquals(afipAudit.getAdmissionDate(), actualAfipAudit.getAdmissionDate());
        assertEquals(afipAudit.getFeatures().getAuditType().getAuditType(),
                actualAfipAudit.getFeatures().getAuditType().getAuditType());
        assertEquals(afipAudit.getFeatures().getAnswer().getAnswer(), actualAfipAudit.getFeatures().getAnswer().getAnswer());
        assertEquals(afipAudit.getFeatures().getAudited().getAudited(), actualAfipAudit.getFeatures().getAudited().getAudited());
    }

    @Test
    public void testDeleteAfipAudit() throws ResourceNotFoundException {

        Mockito.when(afipAuditRepository.findById(1)).thenReturn(Optional.of(afipAudit));
        afipAuditService.deleteAfipAudit(afipAudit.getId());

        verify(afipAuditRepository).deleteById(1);
    }
}

