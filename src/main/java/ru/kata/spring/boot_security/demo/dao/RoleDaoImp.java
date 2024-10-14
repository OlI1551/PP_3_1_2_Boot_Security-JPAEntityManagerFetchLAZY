package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.*;
import java.util.List;


@Repository
public class RoleDaoImp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findByRolename(String rolename) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :rolename", Role.class); // запрос JPQL
        query.setParameter("rolename", rolename);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Role> getRolesList() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }
    @Override
    public Role getRoleById(Long id) {
        if (entityManager.find(Role.class, id) == null) {
            throw new EntityNotFoundException(String.format("Role with ID '%s'  not found", id));
        }
        return entityManager.find(Role.class, id);

    }
    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
