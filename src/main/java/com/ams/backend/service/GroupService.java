package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Group;
import com.ams.backend.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) throws ResourceNotFoundException {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found for this id :: " + id));
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Long id, Group providedGroup) throws ResourceNotFoundException {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found for this id :: " + id));

        group.setGroup(providedGroup.getGroup());

        groupRepository.save(group);
        return group;
    }

    public void deleteGroup(Long id) throws ResourceNotFoundException{
        groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found for this id :: " + id));

        groupRepository.deleteById(id);
    }
}
