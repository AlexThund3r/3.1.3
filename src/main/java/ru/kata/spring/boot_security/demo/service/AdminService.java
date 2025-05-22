package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface AdminService {
    void registerUser(User user, List<Long> roleIds);
    void updateUser(User updatedUser, List<Long> roleIds);
    void deleteUser(Long id);
    User getCurrentUser();
    List<User> getAllUsers();
    User getUserById(Long id);
}