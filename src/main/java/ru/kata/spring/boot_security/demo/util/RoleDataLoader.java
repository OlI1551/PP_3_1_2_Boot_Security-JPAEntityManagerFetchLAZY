package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;


@Component
public class RoleDataLoader implements CommandLineRunner {

    private final RoleDao roleDao;

    @Autowired
    public RoleDataLoader(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void run(String... args) throws Exception {
        roleDao.addRole(new Role(1L, "ROLE_ADMIN"));
        roleDao.addRole(new Role(2L, "ROLE_USER"));
        // Добавляем еще роли при необходимости
    }
}
