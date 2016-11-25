package se.kits.fishregistration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    @JsonIgnore
    private Set<BlogPost> blogPosts;

    @Column
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Fish> fish;

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

    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public Set<Fish> getFish() {
        return fish;
    }

    public void setFish(Set<Fish> fish) {
        this.fish = fish;
    }
}
