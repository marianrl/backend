package com.ams.backend.controller;

import com.ams.backend.entity.Group;
import com.ams.backend.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Test
    public void getAllGroupTest() throws Exception {

        List<Group> groups = new ArrayList<>();
        Mockito.when(groupService.getAllGroup()).thenReturn(groups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/group"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getGroupByIdTest() throws Exception {
        Group group = new Group(1L, "Gestion Laboral S.A.");
        Mockito.when(groupService.getGroupById(1L)).thenReturn(group);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/group/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group").value("Gestion Laboral S.A."));
    }

    @Test
    public void createGroupTest() throws Exception {
        Group group = new Group(null, "Gestion Laboral S.A.");
        Group savedGroup = new Group(1L, "Gestion Laboral S.A.");
        Mockito.when(groupService.createGroup(group)).thenReturn(savedGroup);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"branch\":\"group\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateGroupTest() throws Exception {
        Group updatedGroup = new Group(1L, "Gestion Logistica S.A.");

        Mockito.when(groupService.updateGroup(1L, updatedGroup)).thenReturn(updatedGroup);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/group/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"group\":\"Gestion Logistica S.A.\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(groupService, Mockito.times(1)).updateGroup(ArgumentMatchers.any(), ArgumentMatchers.any(Group.class));
    }

    @Test
    public void deleteGroupTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/group/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(groupService, Mockito.times(1)).deleteGroup(id);
    }

}
