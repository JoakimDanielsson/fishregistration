package se.kits.fishregistration.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by joakim on 2016-11-16.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getBlogPosts",
                query = "SELECT b " +
                        "FROM BlogPost b"
        ),
        @NamedQuery(
                name = "getBlogPostById",
                query = "SELECT b " +
                        "FROM BlogPost b " +
                        "WHERE b.id = :id"
        )
})
@Table(name = "BLOG_POST")
public class BlogPost implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @Column
    private String blogText;

    public BlogPost(User user, String blogText) {
        this.user = user;
        this.blogText = blogText;
    }

    public BlogPost(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }
}