package se.kits.fishregistration.entity;

import javax.persistence.*;
import java.io.Serializable;

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
                name = "deleteUserById",
                query = "DELETE FROM User u " +
                        "WHERE u.id = :id"
        )
})
@Table(name = "USER")
public class User implements Serializable{
    @Id
    @Column (name = "user_id")
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(){
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
}
