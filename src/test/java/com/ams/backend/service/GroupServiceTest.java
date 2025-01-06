package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Group;
import com.ams.backend.repository.GroupRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    private GroupService groupService;

    @BeforeEach
    public void setup() {
        groupService = new GroupService(groupRepository);
    }

    @Test
    public void testGetAllGroupes() {
        List<Group> expectedGroupes = new ArrayList<>();
        Mockito.when(groupRepository.findAll()).thenReturn(expectedGroupes);
        List<Group> actualGroupes = groupService.getAllGroup();

        assertEquals(expectedGroupes, actualGroupes);
    }

    @Test
    public void testGetGroupById() throws ResourceNotFoundException {
        int groupId = 1;
        Group expectedGroup = new Group(groupId, "CABA");

        Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.of(expectedGroup));
        Group actualGroup = groupService.getGroupById(groupId);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void testCreateGroup() {
        int groupId = 1;
        Group group = new Group(groupId, "CABA");

        Mockito.when(groupRepository.save(group)).thenReturn(group);
        Group actualGroup = groupService.createGroup(group);

        assertEquals(actualGroup, group);
    }

    @Test
    public void testUpdateGroup() throws ResourceNotFoundException {
        int groupId = 1;
        Group group = new Group(groupId, "CABA");
        Group updatedGroup = new Group(groupId, "GBA");

        Mockito.when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        Group actualGroup = groupService.updateGroup(groupId, updatedGroup);

        assertEquals(updatedGroup.getId(), actualGroup.getId());
        assertEquals(updatedGroup.getGroup(), actualGroup.getGroup());
    }

    @Test
    public void testDeleteGroup() throws ResourceNotFoundException {
        int groupId = 1;
        Group group = new Group(groupId, "CABA");

        Mockito.when(groupRepository.findById(1)).thenReturn(Optional.of(group));
        groupService.deleteGroup(groupId);

        verify(groupRepository).deleteById(1);
    }
}