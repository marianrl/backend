package com.ams.backend.service;

import com.ams.backend.entity.*;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AfipInputMapper;
import com.ams.backend.repository.AfipInputRepository;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AfipInputServiceTest {

    @Mock
    private AfipInputRepository afipInputRepository;

    @Mock
    private FeaturesRepository featuresRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AuditTypeRepository auditTypeRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private AfipInputMapper afipInputMapper;

    @MockBean
    private AfipInputServiceImpl afipInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "INTERNA");
    final private Audited audited = new Audited(1, "NO");
    final private Features features = new Features(1, auditType, answer);
    final private Audit audit = new Audit(1, LocalDate.now(), auditType, audited);

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
            audit);

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
            audit);

    final private AfipInputResponse afipInputResponse = new AfipInputResponse(
            1,
            "Perez",
            "Juan",
            "20-45125484-7",
            "4568",
            "1248",
            new ClientResponse(1, "hola"),
            "Capital Federal",
            new BranchResponse(1, "hola"),
            LocalDate.now(),
            new FeaturesResponse(1, new AuditTypeResponse(1, "INTERNA"), new AnswerResponse(1, "SE AJUSTA")),
            new AuditResponse(1, LocalDate.now(), new AuditTypeResponse(1, "INTERNA"), new AuditedResponse(1, "NO")));

    final private AfipInputResponse afipInputResponse2 = new AfipInputResponse(
            2,
            "Gomez",
            "Carlos",
            "20-12345678-9",
            "1234",
            "5678",
            new ClientResponse(1, "hola"),
            "Buenos Aires",
            new BranchResponse(1, "hola"),
            LocalDate.of(2023, 1, 1),
            new FeaturesResponse(1, new AuditTypeResponse(1, "INTERNA"), new AnswerResponse(1, "SE AJUSTA")),
            new AuditResponse(1, LocalDate.now(), new AuditTypeResponse(1, "INTERNA"), new AuditedResponse(1, "NO")));

    @BeforeEach
    public void setup() {
        afipInputService = new AfipInputServiceImpl(
                afipInputRepository,
                featuresRepository,
                answerRepository,
                auditTypeRepository,
                auditRepository,
                afipInputMapper);
    }

    @Test
    public void testGetAllAfipInputs_EmptyList() {
        List<AfipInput> emptyList = new ArrayList<>();
        when(afipInputRepository.findAll()).thenReturn(emptyList);

        List<AfipInputResponse> result = afipInputService.getAllAfipInputs();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(afipInputRepository).findAll();
    }

    @Test
    public void testGetAllAfipInputs_WithData() {
        List<AfipInput> expectedList = Arrays.asList(afipInput, afipInput2);
        when(afipInputRepository.findAll()).thenReturn(expectedList);
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);
        when(afipInputMapper.toResponse(afipInput2)).thenReturn(afipInputResponse2);

        List<AfipInputResponse> result = afipInputService.getAllAfipInputs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(afipInputResponse, result.get(0));
        assertEquals(afipInputResponse2, result.get(1));
        verify(afipInputRepository).findAll();
    }

    @Test
    public void testGetAfipInputById_NotFound() {
        when(afipInputRepository.findById(999)).thenReturn(Optional.empty());

        Optional<AfipInputResponse> result = afipInputService.getAfipInputById(999);

        assertFalse(result.isPresent());
        verify(afipInputRepository).findById(999);
    }

    @Test
    public void testGetAfipInputById_Found() {
        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);

        Optional<AfipInputResponse> result = afipInputService.getAfipInputById(1);

        assertTrue(result.isPresent());
        assertEquals(afipInputResponse, result.get());
        verify(afipInputRepository).findById(1);
    }

    @Test
    public void testGetAfipInputByAuditNumber_EmptyList() {
        when(afipInputRepository.findByAudit_Id(999)).thenReturn(new ArrayList<>());

        List<AfipInputResponse> result = afipInputService.getAfipInputByAuditNumber(999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(afipInputRepository).findByAudit_Id(999);
    }

    @Test
    public void testGetAfipInputByAuditNumber_WithData() {
        List<AfipInput> expectedList = Arrays.asList(afipInput, afipInput2);
        when(afipInputRepository.findByAudit_Id(1)).thenReturn(expectedList);
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);
        when(afipInputMapper.toResponse(afipInput2)).thenReturn(afipInputResponse2);

        List<AfipInputResponse> result = afipInputService.getAfipInputByAuditNumber(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(afipInputResponse, result.get(0));
        assertEquals(afipInputResponse2, result.get(1));
        verify(afipInputRepository).findByAudit_Id(1);
    }

    @Test
    public void testDeleteAfipInput_NotFound() {
        when(afipInputRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            afipInputService.deleteAfipInput(999);
        });

        verify(afipInputRepository).findById(999);
        verify(afipInputRepository, never()).deleteById(anyInt());
    }

    @Test
    public void testDeleteAfipInput_Success() throws ResourceNotFoundException {
        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));

        afipInputService.deleteAfipInput(1);

        verify(afipInputRepository).findById(1);
        verify(afipInputRepository).deleteById(1);
    }

    @Test
    public void testCreateAfipInput() throws ResourceNotFoundException {
        InputRequest inputRequest = new InputRequest();
        inputRequest.setLastName("Perez");
        inputRequest.setName("Juan");
        inputRequest.setCuil("20-45125484-7");
        inputRequest.setFile("4568");
        inputRequest.setAllocation("1248");
        inputRequest.setClient(1);
        inputRequest.setUoc("Capital Federal");
        inputRequest.setBranch(1);
        inputRequest.setAuditId(1);
        inputRequest.setAdmissionDate(LocalDate.now());

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.of(features));
        when(afipInputRepository.save(any(AfipInput.class))).thenReturn(afipInput);
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);

        AfipInputResponse result = afipInputService.createAfipInput(inputRequest);

        assertEquals(afipInputResponse, result);
        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(afipInputRepository).save(any(AfipInput.class));
    }

    @Test
    public void testCreateAfipInput_AuditNotFound() {
        InputRequest inputRequest = new InputRequest();
        inputRequest.setAuditId(999);

        when(auditRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> afipInputService.createAfipInput(inputRequest));
        verify(auditRepository).findById(999);
        verify(featuresRepository, never()).findById(anyInt());
        verify(afipInputRepository, never()).save(any());
    }

    @Test
    public void testCreateAfipInput_FeaturesNotFound() {
        InputRequest inputRequest = new InputRequest();
        inputRequest.setAuditId(1);

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> afipInputService.createAfipInput(inputRequest));
        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(afipInputRepository, never()).save(any());
    }

    @Test
    public void testUpdateAfipInput() throws ResourceNotFoundException {
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
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);

        AfipInputResponse updatedAfipInput = afipInputService.updateAfipInput(1, afipInputUpdateRequest);

        assertEquals(afipInputResponse, updatedAfipInput);
        verify(afipInputRepository).findById(1);
        verify(answerRepository).findById(1);
        verify(auditTypeRepository).findById(1);
        verify(featuresRepository).findByAuditTypeAndAnswer(auditType, answer);
        verify(afipInputRepository).save(afipInput);
    }

    @Test
    public void testUpdateAfipInput_AnswerNotFound() {
        AfipInputUpdateRequest updateRequest = new AfipInputUpdateRequest();
        updateRequest.setAnswerId(999);
        updateRequest.setAuditTypeId(1);

        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        when(answerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> afipInputService.updateAfipInput(1, updateRequest));

        verify(afipInputRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).findById(1);
    }

    @Test
    public void testUpdateAfipInput_AuditTypeNotFound() {
        AfipInputUpdateRequest updateRequest = new AfipInputUpdateRequest();
        updateRequest.setAnswerId(1);
        updateRequest.setAuditTypeId(999);
        when(afipInputRepository.findById(1)).thenReturn(Optional.of(afipInput));
        when(answerRepository.findById(1)).thenReturn(Optional.of(answer));
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> afipInputService.updateAfipInput(1, updateRequest));

        verify(afipInputRepository, times(1)).findById(1);
        verify(answerRepository, times(1)).findById(1);
        verify(auditTypeRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateAfipInputs_Success() throws ResourceNotFoundException {
        List<InputRequest> inputRequests = new ArrayList<>();
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setLastName("Perez");
        inputRequest1.setName("Juan");
        inputRequest1.setCuil("20-45125484-7");
        inputRequest1.setFile("4568");
        inputRequest1.setAllocation("1248");
        inputRequest1.setClient(1);
        inputRequest1.setUoc("Capital Federal");
        inputRequest1.setBranch(1);
        inputRequest1.setAuditId(1);
        inputRequest1.setAdmissionDate(LocalDate.now());

        InputRequest inputRequest2 = new InputRequest();
        inputRequest2.setLastName("Gomez");
        inputRequest2.setName("Maria");
        inputRequest2.setCuil("27-12345678-9");
        inputRequest2.setFile("7890");
        inputRequest2.setAllocation("5678");
        inputRequest2.setClient(1);
        inputRequest2.setUoc("Buenos Aires");
        inputRequest2.setBranch(1);
        inputRequest2.setAuditId(1);
        inputRequest2.setAdmissionDate(LocalDate.now());

        inputRequests.add(inputRequest1);
        inputRequests.add(inputRequest2);

        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.of(features));
        when(afipInputRepository.save(any(AfipInput.class)))
                .thenReturn(afipInput)
                .thenReturn(afipInput2);
        when(afipInputMapper.toResponse(afipInput)).thenReturn(afipInputResponse);
        when(afipInputMapper.toResponse(afipInput2)).thenReturn(afipInputResponse2);

        List<AfipInputResponse> result = afipInputService.createAfipInputs(inputRequests);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(afipInputResponse, result.get(0));
        assertEquals(afipInputResponse2, result.get(1));
        verify(auditRepository, times(2)).findById(1);
        verify(featuresRepository, times(2)).findById(49);
        verify(afipInputRepository, times(2)).save(any(AfipInput.class));
    }

    @Test
    public void testCreateAfipInputs_AuditNotFound() {
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setAuditId(1);
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest1);
        when(auditRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            afipInputService.createAfipInputs(inputRequests);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository, never()).findById(anyInt());
        verify(afipInputRepository, never()).save(any(AfipInput.class));
    }

    @Test
    public void testCreateAfipInputs_FeaturesNotFound() {
        InputRequest inputRequest1 = new InputRequest();
        inputRequest1.setAuditId(1);
        List<InputRequest> inputRequests = Collections.singletonList(inputRequest1);
        when(auditRepository.findById(1)).thenReturn(Optional.of(audit));
        when(featuresRepository.findById(49)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            afipInputService.createAfipInputs(inputRequests);
        });

        verify(auditRepository).findById(1);
        verify(featuresRepository).findById(49);
        verify(afipInputRepository, never()).save(any(AfipInput.class));
    }

    @Test
    public void testCreateAfipInputs_EmptyList() throws ResourceNotFoundException {
        List<InputRequest> emptyList = new ArrayList<>();

        List<AfipInputResponse> result = afipInputService.createAfipInputs(emptyList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(auditRepository, never()).findById(anyInt());
        verify(featuresRepository, never()).findById(anyInt());
        verify(afipInputRepository, never()).save(any(AfipInput.class));
    }
}
