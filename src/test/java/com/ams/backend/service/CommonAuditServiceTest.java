package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.CommonAudit;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.CommonAuditRepository;
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
public class CommonAuditServiceTest {

    @Mock
    private CommonAuditRepository commonAuditRepository;

    private CommonAuditService commonAuditService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer ,audited);
    final private CommonAudit commonAudit = new CommonAudit(
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
        commonAuditService = new CommonAuditService(commonAuditRepository);
    }

    @Test
    public void testGetAllCommonAuditService() {
        List<CommonAudit> commonAudits = new ArrayList<>();
        Mockito.when(commonAuditRepository.findAll()).thenReturn(commonAudits);
        List<CommonAudit> actualCommonAudit = commonAuditService.getAllCommonAudits();

        assertEquals(commonAudits, actualCommonAudit);
    }

    @Test
    public void testGetCommonAuditById() throws ResourceNotFoundException {

        Mockito.when(commonAuditRepository.findById(commonAudit.getId())).thenReturn(Optional.of(commonAudit));
        CommonAudit actualCommonAudit = commonAuditService.getCommonAuditById(commonAudit.getId());

        assertEquals(commonAudit, actualCommonAudit);
    }

    @Test
    public void testCreateCommonAudit() {
        Mockito.when(commonAuditRepository.save(commonAudit)).thenReturn(commonAudit);
        CommonAudit actualCommonAudit = commonAuditService.createCommonAudit(commonAudit);

        assertEquals(commonAudit, actualCommonAudit);
    }

    @Test
    public void testUpdateCommonAudit() throws ResourceNotFoundException {
        Mockito.when(commonAuditRepository.findById(1)).thenReturn(Optional.of(commonAudit));

        CommonAudit actualCommonAudit = commonAuditService
                .updateCommonAudit(commonAudit.getId(), commonAudit);

        assertEquals(commonAudit.getAuditDate(), actualCommonAudit.getAuditDate());
        assertEquals(commonAudit.getLastName(), actualCommonAudit.getLastName());
        assertEquals(commonAudit.getName(), actualCommonAudit.getName());
        assertEquals(commonAudit.getCuil(), actualCommonAudit.getCuil());
        assertEquals(commonAudit.getFile(), actualCommonAudit.getFile());
        assertEquals(commonAudit.getAllocation(), actualCommonAudit.getAllocation());
        assertEquals(commonAudit.getClient().getClient(), actualCommonAudit.getClient().getClient());
        assertEquals(commonAudit.getUoc(), actualCommonAudit.getUoc());
        assertEquals(commonAudit.getBranch().getBranch(), actualCommonAudit.getBranch().getBranch());
        assertEquals(commonAudit.getAdmissionDate(), actualCommonAudit.getAdmissionDate());
        assertEquals(commonAudit.getFeatures().getAuditType().getAuditType(),
                actualCommonAudit.getFeatures().getAuditType().getAuditType());
        assertEquals(commonAudit.getFeatures().getAnswer().getAnswer(), actualCommonAudit.getFeatures().getAnswer().getAnswer());
        assertEquals(commonAudit.getFeatures().getAudited().getAudited(), actualCommonAudit.getFeatures().getAudited().getAudited());
    }

    @Test
    public void testDeleteCommonAudit() throws ResourceNotFoundException {

        Mockito.when(commonAuditRepository.findById(1)).thenReturn(Optional.of(commonAudit));
        commonAuditService.deleteCommonAudit(commonAudit.getId());

        verify(commonAuditRepository).deleteById(1);
    }
}

