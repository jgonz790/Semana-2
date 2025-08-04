package com.example;

import java.util.List;

public interface UserDAO {
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    boolean delete(Long id);
    List<User> findByAge(int age);
    User findByEmail(String email);
}