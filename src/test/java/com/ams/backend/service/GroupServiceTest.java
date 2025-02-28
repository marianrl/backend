package com.ams.backend.service;

import com.ams.backend.entity.Group;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    private Group group;

    @BeforeEach
    public void setup() {
        group = new Group();
        group.setId(1);
        group.setGroup("Admin Group");
    }

    @Test
    public void testGetAllGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(group);
        when(groupRepository.findAll()).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.getAllGroup();

        assertEquals(expectedGroups, actualGroups);
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    public void testGetGroupById() throws ResourceNotFoundException {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));

        Group actualGroup = groupService.getGroupById(1);

        assertEquals(group, actualGroup);
        verify(groupRepository, times(1)).findById(1);
    }

    @Test
    public void testGetGroupById_NotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.getGroupById(1));
    }

    @Test
    public void testCreateGroup() {
        when(groupRepository.save(group)).thenReturn(group);

        Group actualGroup = groupService.createGroup(group);

        assertEquals(group, actualGroup);
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    public void testUpdateGroup() throws ResourceNotFoundException {
        Group updatedGroup = new Group();
        updatedGroup.setGroup("Updated Group");

        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        Group actualGroup = groupService.updateGroup(1, updatedGroup);

        assertEquals(updatedGroup.getGroup(), actualGroup.getGroup());
        verify(groupRepository, times(1)).findById(1);
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    public void testUpdateGroup_NotFound() {
        Group updatedGroup = new Group();
        updatedGroup.setGroup("Updated Group");

        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.updateGroup(1, updatedGroup));
    }

    @Test
    public void testDeleteGroup() throws ResourceNotFoundException {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        doNothing().when(groupRepository).deleteById(1);

        groupService.deleteGroup(1);

        verify(groupRepository, times(1)).findById(1);
        verify(groupRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteGroup_NotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupService.deleteGroup(1));
    }
}
