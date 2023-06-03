package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.model.Group;
import com.ams.backend.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/group")
    public List<Group> getAllGroup() {

        return groupService.getAllGroup();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable(value = "id") Long groupId)
            throws ResourceNotFoundException{
        Group group = groupService.getGroupById(groupId);

        return ResponseEntity.ok().body(group);
    }

    @PostMapping("/group")
    public Group createGroup (@Valid @RequestBody Group group)
    {
        return groupService.createGroup(group);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(
            @PathVariable(value = "id") Long groupId,
            @Valid @RequestBody Group groupDetails) throws ResourceNotFoundException {
        final Group updatedGroup = groupService.updateGroup(groupId, groupDetails);

        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable(value = "id") Long groupId)
            throws ResourceNotFoundException {
        groupService.deleteGroup(groupId);

        return ResponseEntity.noContent().build();
    }
}
