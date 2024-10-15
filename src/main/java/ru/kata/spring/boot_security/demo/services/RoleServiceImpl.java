package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional (readOnly = true)
    public Role findByRolename(String rolename) {
        return roleDao.findByRolename(rolename);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Role> getRolesList() {
        return roleDao.getRolesList();
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}