package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.CommonInputMapper;
import com.ams.backend.repository.*;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CommonInputServiceTest {

    @Mock
    private CommonInputRepository commonInputRepository;

    @Mock
    private FeaturesRepository featuresRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private CommonInputMapper mapper;

    @MockBean
    private CommonInputServiceImpl commonInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "INTERNA");
    final private Audited audited = new Audited(1, "NO");
    final private Features features = new Features(1, auditType, answer);
    final private Audit audit = new Audit(1, LocalDate.now(), auditType, audited);

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
            audit);

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
            audit);

    @BeforeEach
    public void setup() {
        commonInputService = new CommonInputServiceImpl(
                commonInputRepository,
                answerRepository,
                featuresRepository,
                auditTypeRepository,
                auditRepository,
                mapper);
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
                "Perez", "Juan", "20-45125484-7", "4568", "1248", 1L, "UOC-123", 1L, LocalDate.now(), 1L);

        assertEquals(expectedCommonInputs, result);

        verify(commonInputRepository).findAll(anyCommonInputSpecification());
    }

    @Test
    public void testCreateCommonInput() throws ResourceNotFoundException {
        // Arrange
        InputRequest inputRequest = new InputRequest();
        inputRequest.setLastName("Perez");
        inputRequest.setName("Juan");
        inputRequest.setCuil("20-45125484-7");
        inputRequest.setFile("4568");
        inputRequest.setAllocation("1248");
        inputRequest.setUoc("UOC-123");
        inputRequest.setAdmissionDate(LocalDate.now());
        inputRequest.setClient(1);
        inputRequest.setBranch(1);
        inputRequest.setAuditId(1);

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.of(features));
        when(commonInputRepository.save(any(CommonInput.class))).thenReturn(commonInput);

        // Act
        CommonInput result = commonInputService.createCommonInput(inputRequest);

        // Assert
        assertNotNull(result);
        assertEquals(commonInput, result);
        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(commonInputRepository).save(any(CommonInput.class));
    }

    @Test
    public void testCreateCommonInput_AuditNotFound() {
        // Arrange
        InputRequest inputRequest = new InputRequest();
        inputRequest.setAuditId(1);

        when(auditRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputService.createCommonInput(inputRequest);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository, never()).findById(anyInt());
        verify(commonInputRepository, never()).save(any(CommonInput.class));
    }

    @Test
    public void testCreateCommonInput_FeaturesNotFound() {
        // Arrange
        InputRequest inputRequest = new InputRequest();
        inputRequest.setAuditId(1);

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputService.createCommonInput(inputRequest);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(commonInputRepository, never()).save(any(CommonInput.class));
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

    @Test
    public void testUpdateCommonInput_AnswerNotFound() {
        CommonInputUpdateRequest updateRequest = new CommonInputUpdateRequest();
        updateRequest.setAnswerId(999);
        updateRequest.setAuditTypeId(1);

        when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commonInputService.updateCommonInput(1, updateRequest));

        verify(commonInputRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).findById(1);
    }

    @Test
    public void testUpdateCommonInput_AuditTypeNotFound() {
        CommonInputUpdateRequest updateRequest = new CommonInputUpdateRequest();
        updateRequest.setAnswerId(1);
        updateRequest.setAuditTypeId(999);
        when(commonInputRepository.findById(1)).thenReturn(Optional.of(commonInput));
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commonInputService.updateCommonInput(1, updateRequest));

        verify(commonInputRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).findById(1);
        verify(auditTypeRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateCommonInputs_Success() throws ResourceNotFoundException {
        // Arrange
        List<InputRequest> inputRequests = new ArrayList<>();
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setLastName("Perez");
        inputRequest1.setName("Juan");
        inputRequest1.setCuil("20-45125484-7");
        inputRequest1.setFile("4568");
        inputRequest1.setAllocation("1248");
        inputRequest1.setUoc("UOC-123");
        inputRequest1.setAdmissionDate(LocalDate.now());
        inputRequest1.setClient(1);
        inputRequest1.setBranch(1);
        inputRequest1.setAuditId(1);

        InputRequest inputRequest2 = new InputRequest();
        inputRequest2.setLastName("Gomez");
        inputRequest2.setName("Maria");
        inputRequest2.setCuil("27-12345678-9");
        inputRequest2.setFile("7890");
        inputRequest2.setAllocation("5678");
        inputRequest2.setUoc("UOC-456");
        inputRequest2.setAdmissionDate(LocalDate.now());
        inputRequest2.setClient(1);
        inputRequest2.setBranch(1);
        inputRequest2.setAuditId(1);

        inputRequests.add(inputRequest1);
        inputRequests.add(inputRequest2);

        List<CommonInput> expectedCommonInputs = Arrays.asList(commonInput, commonInput2);

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.of(features));
        when(commonInputRepository.save(any(CommonInput.class)))
                .thenReturn(commonInput)
                .thenReturn(commonInput2);

        // Act
        List<CommonInput> result = commonInputService.createCommonInputs(inputRequests);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCommonInputs, result);
        verify(auditRepository, times(2)).findById(1);
        verify(featuresRepository, times(2)).findById(49);
        verify(commonInputRepository, times(2)).save(any(CommonInput.class));
    }

    @Test
    public void testCreateCommonInputs_AuditNotFound() {
        // Arrange
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setAuditId(1);
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest1);
        when(auditRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputService.createCommonInputs(inputRequests);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository, never()).findById(anyInt());
        verify(commonInputRepository, never()).save(any(CommonInput.class));
    }

    @Test
    public void testCreateCommonInputs_FeaturesNotFound() {
        // Arrange
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setAuditId(1);
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest1);
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            commonInputService.createCommonInputs(inputRequests);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(commonInputRepository, never()).save(any(CommonInput.class));
    }
}
