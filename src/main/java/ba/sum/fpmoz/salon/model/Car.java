package ba.sum.fpmoz.salon.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String image;

    @Column
    Integer year;

    @Column
    Integer km;

    @Column
    String color;

    @Column
    Boolean sold;

    @ManyToOne
    @JoinColumn(name="motor_id", nullable = true)
    Motor motor;

    @ManyToOne
    @JoinColumn(name="location_id", nullable = true)
    Location location;

    @ManyToOne
    @JoinColumn(name="brand_id", nullable = true)
    Brand brand;

    public Car() {
    }

    public Car(Long id, String name, String description, String image, Integer year, Integer km, String color, Boolean sold, Motor motor, Location location, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.year = year;
        this.km = km;
        this.color = color;
        this.sold = sold;
        this.motor = motor;
        this.location = location;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }
}
