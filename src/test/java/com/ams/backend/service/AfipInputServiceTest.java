package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipInputRepository;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.AfipInputUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AfipInputServiceTest {

    @Mock
    private AfipInputRepository afipInputRepository;

    @Mock
    private FeaturesRepository featuresRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    @MockBean
    private AfipInputService afipInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,1,LocalDate.now(),auditType,audited);

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
            LocalDate.now(),
            features,
            audit
    );

    final private AfipInput afipInput2 = new AfipInput(
            2,
            "Gomez",
            "Carlos",
            "20-12345678-9",
            "1234",
            "5678",
            client,
            "Buenos Aires",
            branch,
            LocalDate.of(2023, 1, 1),
            features,
            audit
    );

    @Before
    public void setup() {
        afipInputService = new AfipInputService(
                afipInputRepository,
                featuresRepository,
                answerRepository,
                auditTypeRepository
        );
    }

    @Test
    public void testGetAllAfipInputService() {
        List<AfipInput> afipInputs = new ArrayList<>();
        when(afipInputRepository.findAll()).thenReturn(afipInputs);
        List<AfipInput> actualAfipInput = afipInputService.getAllAfipInputs();

        assertEquals(afipInputs, actualAfipInput);
    }

    @Test
    public void testGetAfipInputById() {
        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));

        Optional<AfipInput> result = afipInputService.getAfipInputById(1);

        assertTrue(result.isPresent());
        assertEquals(afipInput, result.get());

        verify(afipInputRepository).findById(1);
    }

    @Test
    public void testGetAfipInputByAuditNumber() {

        List<AfipInput> afipInputList = new ArrayList<>();
        afipInputList.add(afipInput);

        when(afipInputRepository.findByAudit_AuditNumber(afipInput.getId())).thenReturn(afipInputList);
        List<AfipInput> actualAfipInput = afipInputService.getAfipInputByAuditNumber(afipInput.getId());

        assertEquals(afipInputList, actualAfipInput);
    }

    @Test
    public void testGetFilteredAfipInputs_Success() {

        List<AfipInput> expectedAfipInputs = Arrays.asList(afipInput, afipInput2);

        when(afipInputRepository.findAll(any(Specification.class))).thenReturn(expectedAfipInputs);

        List<AfipInput> result = afipInputService.getFilteredAfipInputs(
                "Perez", "Juan", "20-45125484-7", "4568", "1248", 1L, "UOC-123", 1L, LocalDate.now(), 1L
        );

        assertEquals(expectedAfipInputs, result);

        verify(afipInputRepository).findAll(any(Specification.class));
    }

    @Test
    public void testCreateAfipAudit() {
        when(afipInputRepository.save(afipInput)).thenReturn(afipInput);
        AfipInput actualAfipInput = afipInputService.createAfipInput(afipInput);

        assertEquals(afipInput, actualAfipInput);
    }

    @Test
    public void testUpdateAfipAudit() throws ResourceNotFoundException {
        AfipInputUpdateRequest afipInputUpdateRequest = new AfipInputUpdateRequest();
        afipInputUpdateRequest.setLastName("Gomez");
        afipInputUpdateRequest.setName("Carlos");
        afipInputUpdateRequest.setCuil("20-12345678-9");
        afipInputUpdateRequest.setFile("1234");
        afipInputUpdateRequest.setAllocation("5678");
        afipInputUpdateRequest.setUoc("UOC-123");
        afipInputUpdateRequest.setAdmissionDate(LocalDate.of(2023, 1, 1));
        afipInputUpdateRequest.setAnswerId(1);
        afipInputUpdateRequest.setAuditTypeId(1);

        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(featuresRepository.findByAuditTypeAndAnswer(auditType, answer)).thenReturn(features);
        when(afipInputRepository.save(afipInput)).thenReturn(afipInput);

        AfipInput updatedAfipInput = afipInputService.updateAfipInput(1, afipInputUpdateRequest);

        assertEquals("Gomez", updatedAfipInput.getLastName());
        assertEquals("Carlos", updatedAfipInput.getName());
        assertEquals("20-12345678-9", updatedAfipInput.getCuil());
        assertEquals("1234", updatedAfipInput.getFile());
        assertEquals("5678", updatedAfipInput.getAllocation());
        assertEquals("UOC-123", updatedAfipInput.getUoc());
        assertEquals(LocalDate.of(2023, 1, 1), updatedAfipInput.getAdmissionDate());
        assertEquals(features, updatedAfipInput.getFeatures());

        verify(afipInputRepository).findById(1);
        verify(answerRepository).findById(1);
        verify(auditTypeRepository).findById(1);
        verify(featuresRepository).findByAuditTypeAndAnswer(auditType, answer);
        verify(afipInputRepository).save(afipInput);
    }

    @Test
    public void testDeleteAfipAudit() throws ResourceNotFoundException {

        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        afipInputService.deleteAfipInput(afipInput.getId());

        verify(afipInputRepository).deleteById(1);
    }
}

