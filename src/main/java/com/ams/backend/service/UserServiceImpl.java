package com.ams.backend.service;

import com.ams.backend.entity.User;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.UserRepository;
import com.ams.backend.request.UserMail;
import com.ams.backend.service.interfaces.UserService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    public User getUserByMailAndPassword(String mail, String password) {
        return userRepository.findByMailAndPassword(mail, password);
    }

    public User getUserByMail(UserMail mail) {
        return userRepository.findByMail(mail.getMail());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) throws ResourceNotFoundException {
        User user1 = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        user1.setMail(user.getMail());
        user1.setPassword(user.getPassword());
        user1.setRole(user.getRole());

        userRepository.save(user1);

        return user1;
    }

    public void deleteUser(int id) throws ResourceNotFoundException{
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        userRepository.deleteById(id);
    }
}
