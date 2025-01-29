package com.ams.backend.controller;

import com.ams.backend.entity.Group;
import com.ams.backend.service.interfaces.GroupService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private Group group;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        group = new Group();
        group.setId(1);
    }

    @Test
    public void getAllGroupTest() throws Exception {
        List<Group> groups = Collections.singletonList(group);
        when(groupService.getAllGroup()).thenReturn(groups);

        List<Group> result = groupController.getAllGroup();

        assertEquals(groups, result);
        verify(groupService, times(1)).getAllGroup();
    }

    @Test
    public void getGroupByIdTest() throws Exception {
        when(groupService.getGroupById(1)).thenReturn(group);

        ResponseEntity<Group> result = groupController.getGroupById(1);

        assertEquals(group, result.getBody());
        verify(groupService, times(1)).getGroupById(1);
    }

    @Test
    public void createGroupTest() throws Exception {
        when(groupService.createGroup(any(Group.class))).thenReturn(group);

        Group result = groupController.createGroup(group);

        assertEquals(group, result);
        verify(groupService, times(1)).createGroup(any(Group.class));
    }

    @Test
    public void updateGroupTest() throws Exception {
        Group group = new Group();
        when(groupService.updateGroup(eq(1), any(Group.class))).thenReturn(group);

        ResponseEntity<Group> result = groupController.updateGroup(1, group);

        assertEquals(group, result.getBody());
        verify(groupService, times(1)).updateGroup(eq(1), any(Group.class));
    }

    @Test
    public void deleteGroupTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(groupService).deleteGroup(1);

        ResponseEntity<Void> result = groupController.deleteGroup(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(groupService, times(1)).deleteGroup(1);
    }

}
