package se.kits.fishregistration.boundary;

import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by joakim on 2016-11-11.
 */

@Stateless
public class UserManager {

    @PersistenceContext(name = "ds")
    private EntityManager em;

    public List<User> getUsers() {
        return em.createNamedQuery("getUsers", User.class).getResultList();
    }

    public User getUserById(Long id) {
        return em.createNamedQuery("getUserById", User.class).setParameter("id", id).getSingleResult();
    }

    public User createUser(User user) {
        return em.merge(user);
    }

    public void deleteUser(Long id) { em.remove(getUserById(id)); }
}