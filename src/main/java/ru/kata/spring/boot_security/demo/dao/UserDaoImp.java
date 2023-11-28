package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   @Transactional(readOnly = true)
   @Query("Select u from User u left join fetch u.roles")
   public List<User> getUsersList() {
      return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
   }

   @Override
   @Transactional(readOnly = true)
   public User getUserById(Long id) {
      return entityManager.find(User.class, id);
   }

   @Override
   @Transactional
   public Optional<User> findUserById(Long id) {
      return Optional.ofNullable(entityManager.find(User.class, id));
   }

   @Override
   @Transactional(readOnly = true)
   @Query("Select u from User u left join fetch u.roles where u.username=:username")
   public User findByUsername(String username) {
      TypedQuery<User> query = entityManager.createQuery(
              "SELECT u FROM User u WHERE u.username = :username", User.class); // запрос JPQL
      query.setParameter("username", username);
      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   @Transactional
   public boolean existsById(Long id) {
      return entityManager.find(User.class, id) != null;
   }

   @Override
   @Transactional
   public void addUser(User user) {
      entityManager.persist(user);
   }

   @Override
   @Transactional
   public void updateUser(User user) {
      entityManager.merge(user);
   }

   @Override
   @Transactional
   public void deleteUserById(Long id) {
      User user = entityManager.find(User.class, id);
      if (user != null) {
         entityManager.remove(user);
      }
   }
}
