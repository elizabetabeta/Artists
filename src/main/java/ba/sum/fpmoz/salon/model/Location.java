package ba.sum.fpmoz.salon.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column
    String address;

    @OneToMany(mappedBy = "location")
    List<Car> cars;

    public Location() {
    }

    public Location(Long id, String name, String address, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cars = cars;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
