package se.kits.fishregistration.boundary;

import se.kits.fishregistration.entity.Fish;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.lang.*;

/**
 * Created by joakim on 2016-10-24.
 */

@Stateless
public class FishManager {

    @PersistenceContext(name = "ds")
    private EntityManager em;


    public List<Fish> getAllFish() {
        return em.createNamedQuery("getAllFish", Fish.class)
                .getResultList();
    }

    public Fish getFishById(Long id) {
        return em.createNamedQuery("getFishById", Fish.class).setParameter("id", id).getSingleResult();
    }

//    public Fish createFish(double weight, double length, double longitude,
//                           double latitude, String species, User user) {
//        return em.merge(new Fish(weight, length, longitude, latitude, species, user));
//    }

    public Fish createFish(Fish fish) {
        return em.merge(fish);
    }

    public void deleteFish(Long id) {
        em.remove(getFishById(id));
    }
}
