package se.kits.fishregistration.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

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

    @Column
    private Date date;

    public BlogPost(User user, String blogText) {
        this.user = user;
        this.blogText = blogText;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Gothenburg"));
        this.date = new Date(calendar.getTime().getTime());
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}