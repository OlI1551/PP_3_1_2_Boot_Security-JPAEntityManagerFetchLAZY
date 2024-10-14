package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional (readOnly = true)
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userDao.findUserById(userId);
        return userFromDb.orElse(null);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional (readOnly = true)
    public List<User> allUsers() {
        return userDao.getUsersList();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional (readOnly = true)
    public List<User> getUsersList(int limit) {
        if (limit <= 0 || limit >= userDao.getUsersList().size()) {
            return userDao.getUsersList();
        } else {
            return userDao.getUsersList().stream().limit(limit).collect(Collectors.toList());
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public boolean addUser(User user, boolean roleAdmin) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.findByRolename("ROLE_USER"));
        if (roleAdmin) {
            roles.add(roleDao.findByRolename("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userDao.addUser(user);
        return true;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public void updateUser(User user, boolean roleAdmin) {
        User userFromDB = userDao.getUserById(user.getId());
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setUsername(user.getUsername());
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.findByRolename("ROLE_USER"));
        if (roleAdmin) {
            roles.add(roleDao.findByRolename("ROLE_ADMIN"));
        }
        userFromDB.setRoles(roles);
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.updateUser(userFromDB);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userDao.deleteUserById(userId);
    }
}
