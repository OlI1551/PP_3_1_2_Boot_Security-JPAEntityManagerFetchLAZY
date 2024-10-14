package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
   @PersistenceContext
   private EntityManager entityManager;
   @Override
   public Optional<User> findUserById(Long id) {
      return Optional.ofNullable(entityManager.find(User.class, id));
   }

   @Override
   public User findByUsername(String username) {
      TypedQuery<User> query = entityManager.createQuery(
              "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class); // запрос JPQL
      query.setParameter("username", username);
      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   public boolean existsById(Long id) {
      return entityManager.find(User.class, id) != null;
   }

   @Override
   public List<User> getUsersList() {
      return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles", User.class).getResultList();
   }

   @Override
   public User getUserById(Long id) {
      if (entityManager.find(User.class, id) == null) {
         throw new EntityNotFoundException(String.format("User with ID '%s'  not found", id));
      }
      return entityManager.find(User.class, id);
   }

   @Override
   public void addUser(User user) {
      entityManager.persist(user);
   }

   @Override
   public void updateUser(User user) {
      if (entityManager.find(User.class, user.getId()) == null) {
         throw new EntityNotFoundException(String.format("User with ID '%s'  not found", user.getId()));
      }
      entityManager.merge(user);
   }

   @Override
   public void deleteUserById(Long id) {
      User user = entityManager.find(User.class, id);
      if (user == null) {
         throw new EntityNotFoundException(String.format("User with ID '%s'  not found", id));
      }
      entityManager.remove(user);
   }
}
