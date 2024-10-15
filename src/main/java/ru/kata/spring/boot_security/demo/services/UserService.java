package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;


public interface UserService {
    List<User> allUsers();
    List<User> getUsersList(int limit);
    User findUserById(Long userId);
    User getUserById(Long userId);
    boolean addUser(User user, boolean roleAdmin);
    void updateUser(User user, boolean roleAdmin);
    void deleteUser(Long userId);


}
