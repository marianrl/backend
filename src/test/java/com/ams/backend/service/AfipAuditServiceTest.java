package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipInputRepository;
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
    private AfipInputRepository afipInputRepository;

    private AfipInputService afipInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,1,"11/10/2023",auditType,audited);

    final private AfipInput afipInput = new AfipInput(
            1,
            "Perez",
            "Juan",
            "20-45125484-7",
            "4568",
            "1248",
            client,
            "Capital Federal",
            branch,
            "17/06/2023",
            features,
            audit
    );

    @Before
    public void setup() {
        afipInputService = new AfipInputService(afipInputRepository);
    }

    @Test
    public void testGetAllAfipInputService() {
        List<AfipInput> afipInputs = new ArrayList<>();
        Mockito.when(afipInputRepository.findAll()).thenReturn(afipInputs);
        List<AfipInput> actualAfipInput = afipInputService.getAllAfipInputs();

        assertEquals(afipInputs, actualAfipInput);
    }

    @Test
    public void testGetAfipInputByAuditNumber() {

        List<AfipInput> afipInputList = new ArrayList<>();
        afipInputList.add(afipInput);

        Mockito.when(afipInputRepository.findByAuditNumber(afipInput.getId())).thenReturn(afipInputList);
        List<AfipInput> actualAfipInput = afipInputService.getAfipInputByAuditNumber(afipInput.getId());

        assertEquals(afipInputList, actualAfipInput);
    }

    @Test
    public void testCreateAfipAudit() {
        Mockito.when(afipInputRepository.save(afipInput)).thenReturn(afipInput);
        AfipInput actualAfipInput = afipInputService.createAfipInput(afipInput);

        assertEquals(afipInput, actualAfipInput);
    }

    @Test
    public void testUpdateAfipAudit() throws ResourceNotFoundException {
        Mockito.when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));

        AfipInput actualAfipInput = afipInputService
                .updateAfipInput(afipInput.getId(), afipInput);

        assertEquals(afipInput.getLastName(), actualAfipInput.getLastName());
        assertEquals(afipInput.getName(), actualAfipInput.getName());
        assertEquals(afipInput.getCuil(), actualAfipInput.getCuil());
        assertEquals(afipInput.getFile(), actualAfipInput.getFile());
        assertEquals(afipInput.getAllocation(), actualAfipInput.getAllocation());
        assertEquals(afipInput.getClient().getClient(), actualAfipInput.getClient().getClient());
        assertEquals(afipInput.getUoc(), actualAfipInput.getUoc());
        assertEquals(afipInput.getBranch().getBranch(), actualAfipInput.getBranch().getBranch());
        assertEquals(afipInput.getAdmissionDate(), actualAfipInput.getAdmissionDate());
        assertEquals(afipInput.getFeatures().getAuditType().getAuditType(),
                actualAfipInput.getFeatures().getAuditType().getAuditType());
        assertEquals(afipInput.getFeatures().getAnswer().getAnswer(), actualAfipInput.getFeatures().getAnswer().getAnswer());
        assertEquals(afipInput.getAudit().getAuditNumber(), actualAfipInput.getAudit().getAuditNumber());

    }

    @Test
    public void testDeleteAfipAudit() throws ResourceNotFoundException {

        Mockito.when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        afipInputService.deleteAfipInput(afipInput.getId());

        verify(afipInputRepository).deleteById(1);
    }
}

