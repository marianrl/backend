package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Group;
import com.ams.backend.repository.GroupRepository;
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
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    private GroupService groupService;

    @Before
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
        Long groupId = 1L;
        Group expectedGroup = new Group(groupId, "CABA");

        Mockito.when(groupRepository.findById(groupId)).thenReturn(Optional.of(expectedGroup));
        Group actualGroup = groupService.getGroupById(groupId);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void testCreateGroup() {
        Long groupId = 1L;
        Group group = new Group(groupId, "CABA");

        Mockito.when(groupRepository.save(group)).thenReturn(group);
        Group actualGroup = groupService.createGroup(group);

        assertEquals(actualGroup, group);
    }

    @Test
    public void testUpdateGroup() throws ResourceNotFoundException {
        Long groupId = 1L;
        Group group = new Group(groupId, "CABA");
        Group updatedGroup = new Group(groupId, "GBA");

        Mockito.when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        Group actualGroup = groupService.updateGroup(groupId, updatedGroup);

        assertEquals(updatedGroup.getId(), actualGroup.getId());
        assertEquals(updatedGroup.getGroup(), actualGroup.getGroup());
    }

    @Test
    public void testDeleteGroup() throws ResourceNotFoundException {
        Long groupId = 1L;
        Group group = new Group(groupId, "CABA");

        Mockito.when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        groupService.deleteGroup(groupId);

        verify(groupRepository).deleteById(1L);
    }
}