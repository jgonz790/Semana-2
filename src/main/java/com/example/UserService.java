package com.example;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User createUser(String name, String email, int age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 0 and 120");
        }

        User existingUser = userDAO.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User newUser = new User(null, name, email, age);
        return userDAO.save(newUser);
    }

    public boolean deleteUser(Long id) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }
        return userDAO.delete(id);
    }

    public List<User> getUsersByAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        return userDAO.findByAge(age);
    }

    public boolean isAdult(Long userId) {
        User user = getUserById(userId);
        return user != null && user.getAge() >= 18;
    }
}