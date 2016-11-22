package se.kits.fishregistration.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by joakim on 2016-11-11.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getUsers",
                query = "SELECT u " +
                        "FROM User u"
        ),
        @NamedQuery(
                name = "getUserById",
                query = "SELECT u " +
                        "FROM User u " +
                        "WHERE u.id = :id"
        )
})
@Table(name = "USER")
public class User implements Serializable {
    @Id
    @Column(name="user_id")
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BlogPost> blogPosts;

    @Column
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Fish> fish;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public List<Fish> getFish() {
        return fish;
    }

    public void setFish(List<Fish> fish) {
        this.fish = fish;
    }
}
