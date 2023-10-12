package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.CommonInputRepository;
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
public class CommonInputServiceTest {

    @Mock
    private CommonInputRepository commonInputRepository;

    private CommonInputService commonInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,1,"11/10/2023",auditType,audited);

    final private CommonInput commonInput = new CommonInput(
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
        commonInputService = new CommonInputService(commonInputRepository);
    }

    @Test
    public void testGetAllCommonInputService() {
        List<CommonInput> commonInputs = new ArrayList<>();
        Mockito.when(commonInputRepository.findAll()).thenReturn(commonInputs);
        List<CommonInput> actualCommonInput = commonInputService.getAllCommonInputs();

        assertEquals(commonInputs, actualCommonInput);
    }

    @Test
    public void testGetCommonInputById() throws ResourceNotFoundException {

        Mockito.when(commonInputRepository.findById(commonInput.getId())).thenReturn(Optional.of(commonInput));
        List <CommonInput> actualCommonInput = commonInputService.getCommonInputById(commonInput.getId());

        assertEquals(commonInput, actualCommonInput);
    }

    @Test
    public void testCreateCommonAudit() {
        Mockito.when(commonInputRepository.save(commonInput)).thenReturn(commonInput);
        CommonInput actualCommonInput = commonInputService.createCommonInput(commonInput);

        assertEquals(commonInput, actualCommonInput);
    }

    @Test
    public void testUpdateCommonAudit() throws ResourceNotFoundException {
        Mockito.when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));

        CommonInput actualCommonInput = commonInputService
                .updateCommonInput(commonInput.getId(), commonInput);

        assertEquals(commonInput.getLastName(), actualCommonInput.getLastName());
        assertEquals(commonInput.getName(), actualCommonInput.getName());
        assertEquals(commonInput.getCuil(), actualCommonInput.getCuil());
        assertEquals(commonInput.getFile(), actualCommonInput.getFile());
        assertEquals(commonInput.getAllocation(), actualCommonInput.getAllocation());
        assertEquals(commonInput.getClient().getClient(), actualCommonInput.getClient().getClient());
        assertEquals(commonInput.getUoc(), actualCommonInput.getUoc());
        assertEquals(commonInput.getBranch().getBranch(), actualCommonInput.getBranch().getBranch());
        assertEquals(commonInput.getAdmissionDate(), actualCommonInput.getAdmissionDate());
        assertEquals(commonInput.getFeatures().getAuditType().getAuditType(),
                actualCommonInput.getFeatures().getAuditType().getAuditType());
        assertEquals(commonInput.getFeatures().getAnswer().getAnswer(), actualCommonInput.getFeatures().getAnswer().getAnswer());
        assertEquals(commonInput.getAudit().getAuditNumber(), actualCommonInput.getAudit().getAuditNumber());
    }

    @Test
    public void testDeleteCommonAudit() throws ResourceNotFoundException {

        Mockito.when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));
        commonInputService.deleteCommonInput(commonInput.getId());

        verify(commonInputRepository).deleteById(1);
    }
}

