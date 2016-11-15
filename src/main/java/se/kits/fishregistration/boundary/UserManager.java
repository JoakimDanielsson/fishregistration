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
        return em.createNamedQuery("getUsers", User.class)
                .getResultList();
    }
    public User createUser(String firstName, String lastName) {
        return em.merge(new User(firstName, lastName));
    }

    public void deleteUser(Long id) {
        em.createNamedQuery("deleteUserById")
                .setParameter("id", id)
                .executeUpdate();
    }
}