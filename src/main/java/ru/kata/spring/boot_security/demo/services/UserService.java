package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {


    List<User> allUsers();
    List<User> getUsersList(int limit);
    User findUserById(Long userId);
    void addUser(User user, boolean roleAdmin);
    void updateUser(User user, boolean roleAdmin);
    void deleteUser(Long userId);


}
