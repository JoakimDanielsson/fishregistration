package se.kits.fishregistration.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String blogText;

    @Column
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}