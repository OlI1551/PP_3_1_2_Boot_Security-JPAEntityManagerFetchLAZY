package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.HashSet;
import java.util.Set;


@Service
public class RegistrationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(User user, boolean roleAdmin) {
        User userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_USER"));
        if (!userDao.existsById(1L) || roleAdmin) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return true;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean saveUser(User user, boolean roleAdmin) {
        User userFromDB = userDao.getUserById(user.getId());
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setUsername(user.getUsername());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setPasswordConfirm(user.getPasswordConfirm());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_USER"));
        if (!userDao.existsById(1L) || roleAdmin) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
        return true;
    }
}
