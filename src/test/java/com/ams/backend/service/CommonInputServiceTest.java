package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.CommonInputRepository;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.CommonInputUpdateRequest;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommonInputServiceTest {

    @Mock
    private CommonInputRepository commonInputRepository;

    @Mock
    private FeaturesRepository featuresRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    @MockBean
    private CommonInputService commonInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "INTERNA");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,LocalDate.now(),auditType,audited);

    private static Specification<CommonInput> anyCommonInputSpecification() {
        return argThat(argument -> true); // Acepta cualquier Specification<AfipInput>
    }

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
            LocalDate.now(),
            features,
            audit
    );

    final private CommonInput commonInput2 = new CommonInput(
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
        commonInputService = new CommonInputService(
                commonInputRepository,
                answerRepository,
                featuresRepository,
                auditTypeRepository
        );
    }

    @Test
    public void testGetAllCommonInputService() {
        List<CommonInput> commonInputs = new ArrayList<>();
        when(commonInputRepository.findAll()).thenReturn(commonInputs);
        List<CommonInput> actualCommonInput = commonInputService.getAllCommonInputs();

        assertEquals(commonInputs, actualCommonInput);
    }

    @Test
    public void testGetCommonInputById() {
        when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));

        Optional<CommonInput> result = commonInputService.getCommonInputById(1);

        assertTrue(result.isPresent());
        assertEquals(commonInput, result.get());

        verify(commonInputRepository).findById(1);
    }

    @Test
    public void testGetCommonInputByAuditNumber() {

        List<CommonInput> commonInputList = new ArrayList<>();
        commonInputList.add(commonInput);

        when(commonInputRepository.findByAudit_Id(commonInput.getId())).thenReturn(commonInputList);
        List<CommonInput> actualCommonInput = commonInputService.getCommonInputByAuditNumber(commonInput.getId());

        assertEquals(commonInputList, actualCommonInput);
    }

    @Test
    public void testGetFilteredCommonInputs_Success() {

        List<CommonInput> expectedCommonInputs = Arrays.asList(commonInput, commonInput2);

        when(commonInputRepository.findAll(anyCommonInputSpecification())).thenReturn(expectedCommonInputs);

        List<CommonInput> result = commonInputService.getFilteredCommonInputs(
                "Perez", "Juan", "20-45125484-7", "4568", "1248", 1L, "UOC-123", 1L, LocalDate.now(), 1L
        );

        assertEquals(expectedCommonInputs, result);

        verify(commonInputRepository).findAll(anyCommonInputSpecification());
    }

    @Test
    public void testCreateCommonAudit() {
        when(commonInputRepository.save(commonInput)).thenReturn(commonInput);
        CommonInput actualCommonInput = commonInputService.createCommonInput(commonInput);

        assertEquals(commonInput, actualCommonInput);
    }

    @Test
    public void testUpdateCommonAudit() throws ResourceNotFoundException {
        CommonInputUpdateRequest commonInputUpdateRequest = new CommonInputUpdateRequest();
        commonInputUpdateRequest.setLastName("Gomez");
        commonInputUpdateRequest.setName("Carlos");
        commonInputUpdateRequest.setCuil("20-12345678-9");
        commonInputUpdateRequest.setFile("1234");
        commonInputUpdateRequest.setAllocation("5678");
        commonInputUpdateRequest.setUoc("UOC-123");
        commonInputUpdateRequest.setAdmissionDate(LocalDate.of(2023, 1, 1));
        commonInputUpdateRequest.setAnswerId(1);
        commonInputUpdateRequest.setAuditTypeId(1);

        when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(featuresRepository.findByAuditTypeAndAnswer(auditType, answer)).thenReturn(features);
        when(commonInputRepository.save(commonInput)).thenReturn(commonInput);

        CommonInput updatedCommonInput = commonInputService.updateCommonInput(1, commonInputUpdateRequest);

        assertEquals("Gomez", updatedCommonInput.getLastName());
        assertEquals("Carlos", updatedCommonInput.getName());
        assertEquals("20-12345678-9", updatedCommonInput.getCuil());
        assertEquals("1234", updatedCommonInput.getFile());
        assertEquals("5678", updatedCommonInput.getAllocation());
        assertEquals("UOC-123", updatedCommonInput.getUoc());
        assertEquals(LocalDate.of(2023, 1, 1), updatedCommonInput.getAdmissionDate());
        assertEquals(features, updatedCommonInput.getFeatures());

        verify(commonInputRepository).findById(1);
        verify(answerRepository).findById(1);
        verify(auditTypeRepository).findById(1);
        verify(featuresRepository).findByAuditTypeAndAnswer(auditType, answer);
        verify(commonInputRepository).save(commonInput);
    }

    @Test
    public void testDeleteCommonAudit() throws ResourceNotFoundException {

        when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));
        commonInputService.deleteCommonInput(commonInput.getId());

        verify(commonInputRepository).deleteById(1);
    }
    
    
}

