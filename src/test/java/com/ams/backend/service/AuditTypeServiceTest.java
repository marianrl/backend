package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AuditTypeMapper;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AuditTypeServiceTest {

    @Mock
    private AuditTypeRepository auditTypeRepository;

    @Mock
    private AuditTypeMapper auditTypeMapper;

    private AuditTypeServiceImpl auditTypeService;

    private AuditType auditType;
    private AuditTypeResponse auditTypeResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditTypeService = new AuditTypeServiceImpl(auditTypeRepository, auditTypeMapper);

        // Create test data
        auditType = new AuditType();
        auditType.setId(1);
        auditType.setAuditType("Financial Audit");

        auditTypeResponse = new AuditTypeResponse(1, "Financial Audit");
    }

    @Test
    public void testGetAllAuditType() {
        List<AuditType> auditTypes = Arrays.asList(auditType);

        when(auditTypeRepository.findAll()).thenReturn(auditTypes);
        when(auditTypeMapper.toResponse(auditType)).thenReturn(auditTypeResponse);

        List<AuditTypeResponse> result = auditTypeService.getAllAuditType();

        assertEquals(1, result.size());
        assertEquals(auditTypeResponse.getAuditType(), result.get(0).getAuditType());
        verify(auditTypeRepository, times(1)).findAll();
        verify(auditTypeMapper, times(1)).toResponse(auditType);
    }

    @Test
    public void testGetAuditTypeById_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(auditTypeMapper.toResponse(auditType)).thenReturn(auditTypeResponse);

        AuditTypeResponse result = auditTypeService.getAuditTypeById(1);

        assertNotNull(result);
        assertEquals(auditTypeResponse.getAuditType(), result.getAuditType());
        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditTypeMapper, times(1)).toResponse(auditType);
    }

    @Test
    public void testGetAuditTypeById_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.getAuditTypeById(999));

        verify(auditTypeRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateAuditType() {
        AuditTypeRequest newRequest = new AuditTypeRequest(0, "Operational Audit");
        AuditType newAuditType = new AuditType();
        newAuditType.setAuditType("Operational Audit");
        AuditTypeResponse newResponse = new AuditTypeResponse(1, "Operational Audit");

        when(auditTypeMapper.toEntity(newRequest)).thenReturn(newAuditType);
        when(auditTypeRepository.save(newAuditType)).thenReturn(newAuditType);
        when(auditTypeMapper.toResponse(newAuditType)).thenReturn(newResponse);

        AuditTypeResponse result = auditTypeService.createAuditType(newRequest);

        assertNotNull(result);
        assertEquals("Operational Audit", result.getAuditType());
        verify(auditTypeRepository, times(1)).save(newAuditType);
        verify(auditTypeMapper, times(1)).toEntity(newRequest);
        verify(auditTypeMapper, times(1)).toResponse(newAuditType);
    }

    @Test
    public void testUpdateAuditType_AuditTypeFound() throws ResourceNotFoundException {
        AuditTypeRequest updateRequest = new AuditTypeRequest(1, "Updated Financial Audit");
        AuditType updatedAuditType = new AuditType();
        updatedAuditType.setAuditType("Updated Financial Audit");
        AuditTypeResponse updatedResponse = new AuditTypeResponse(1, "Updated Financial Audit");

        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        when(auditTypeRepository.save(any(AuditType.class))).thenReturn(updatedAuditType);
        when(auditTypeMapper.toResponse(updatedAuditType)).thenReturn(updatedResponse);

        AuditTypeResponse result = auditTypeService.updateAuditType(1, updateRequest);

        assertNotNull(result);
        assertEquals("Updated Financial Audit", result.getAuditType());
        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditTypeRepository, times(1)).save(any(AuditType.class));
        verify(auditTypeMapper, times(1)).toResponse(updatedAuditType);
    }

    @Test
    public void testUpdateAuditType_AuditTypeNotFound() {
        AuditTypeRequest updateRequest = new AuditTypeRequest(999, "Updated Financial Audit");

        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.updateAuditType(999, updateRequest));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).save(any(AuditType.class));
    }

    @Test
    public void testDeleteAuditType_AuditTypeFound() throws ResourceNotFoundException {
        when(auditTypeRepository.findById(1)).thenReturn(Optional.of(auditType));
        doNothing().when(auditTypeRepository).deleteById(1);

        auditTypeService.deleteAuditType(1);

        verify(auditTypeRepository, times(1)).findById(1);
        verify(auditTypeRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAuditType_AuditTypeNotFound() {
        when(auditTypeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditTypeService.deleteAuditType(999));

        verify(auditTypeRepository, times(1)).findById(999);
        verify(auditTypeRepository, times(0)).deleteById(999);
    }
}
