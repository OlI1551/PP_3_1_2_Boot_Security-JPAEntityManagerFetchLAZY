package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;


public interface UserDao {
   User findByUsername(String username);
   boolean existsById(Long id);
   List<User> getUsersList();
   Optional<User> findUserById(Long id);
   User getUserById(Long id);
   void addUser(User user);
   void updateUser(User user);
   void deleteUserById(Long id);
}
