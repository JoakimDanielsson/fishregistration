package se.kits.fishregistration.boundary;

import se.kits.fishregistration.entity.BlogPost;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by joakim on 2016-11-16.
 */

@Stateless
public class BlogPostManager {

    @PersistenceContext(name = "ds")
    private EntityManager em;

    public List<BlogPost> getAllBlogPosts() {
        return em.createNamedQuery("getBlogPosts", BlogPost.class).getResultList();
    }

    public BlogPost getBlogPostById(Long id) {
        return em.createNamedQuery("getBlogPostById", BlogPost.class).setParameter("id", id).getSingleResult();
    }

    public BlogPost createBlogPost(User user, String blogText) {
        return em.merge(new BlogPost(user, blogText));
    }

    public void deleteBlogPost(Long id) { em.remove(getBlogPostById(id)); }

}
