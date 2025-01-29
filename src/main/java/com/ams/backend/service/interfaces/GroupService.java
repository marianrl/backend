package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Group;
import com.ams.backend.exception.ResourceNotFoundException;

public interface GroupService {
  List<Group> getAllGroup();
  Group getGroupById(int id) throws ResourceNotFoundException;
  Group createGroup(Group group);
  Group updateGroup(int id, Group providedGroup) throws ResourceNotFoundException;
  void deleteGroup(int id) throws ResourceNotFoundException;
}
