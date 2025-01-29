package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.User;
import com.ams.backend.exception.ResourceNotFoundException;

public interface UserService {
  List<User> getAllUsers();
  User getUserById(int id) throws ResourceNotFoundException;
  User getUserByMailAndPassword(String mail, String password);
  User createUser(User user);
  User updateUser(int id, User user) throws ResourceNotFoundException;
  void deleteUser(int id) throws ResourceNotFoundException;
}
