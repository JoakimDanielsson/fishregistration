package se.kits.fishregistration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by joakim on 2016-10-24.
 */

@Entity
@NamedQueries({
        @NamedQuery(
                name = "findFishById",
                query = "SELECT f " +
                        "FROM Fish f " +
                        "WHERE f.id = :id"
        ),
        @NamedQuery(
                name = "getAllFish",
                query = "SELECT f " +
                        "FROM Fish f"
        ),
        @NamedQuery(
                name = "deleteFishById",
                query = "DELETE FROM Fish f " +
                        "WHERE f.id = :id"
        ),
//        @NamedQuery(
//                name = "getFishBySpecies",
//                query = "SELECT f " +
//                        "FROM Fish f " +
//                        "WHERE f.species = :species"
//        ),
        @NamedQuery(
                name = "getNrFishBySpecies",
                query = "SELECT COUNT(f) " +
                        "FROM Fish f " +
                        "WHERE f.species = :species"
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



    public Fish(double weight, double length, double longitude,
                double latitude, String species) {
        this.weight = weight;
        this.length = length;
        this.longitude = longitude;
        this.latitude = latitude;
        this.species = species;
        this.date = new Date();
    }

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
}