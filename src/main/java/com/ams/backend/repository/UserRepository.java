package com.ams.backend.repository;

import com.ams.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByMailAndPassword(String mail, String password);
    User findByMail(String mail);
}
