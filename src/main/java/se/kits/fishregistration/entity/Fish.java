package se.kits.fishregistration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


/**
 * Created by joakim on 2016-10-24.
 */

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllFish",
                query = "SELECT f " +
                        "FROM Fish f"
        ),
        @NamedQuery(
                name = "getFishById",
                query = "SELECT f " +
                        "FROM Fish f " +
                        "WHERE f.id = :id"
        )
})
@Table(name = "FISH")
public class Fish implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private double weight;

    @Column
    private double length;

    @Column
    private double longitude;

    @Column
    private double latitude;

    @Column
    private String species;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Fish() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}