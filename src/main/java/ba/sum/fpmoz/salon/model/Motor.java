package ba.sum.fpmoz.salon.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="motors")
public class Motor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @OneToMany(mappedBy = "motor")
    List<Car> cars;

    public Motor() {
    }

    public Motor(Long id, String name, String description, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
