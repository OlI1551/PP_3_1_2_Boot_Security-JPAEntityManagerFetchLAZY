package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userDao.findUserById(userId);
        return userFromDb.orElse(null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> allUsers() {
        return userDao.getUsersList();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsersList(int limit) {
        if (limit <= 0 || limit >= userDao.getUsersList().size()) {
            return userDao.getUsersList();
        } else {
            return userDao.getUsersList().stream().limit(limit).collect(Collectors.toList());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(Long userId) {
        userDao.deleteUserById(userId);
    }
}
