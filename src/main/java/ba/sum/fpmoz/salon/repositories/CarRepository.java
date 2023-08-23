package ba.sum.fpmoz.salon.repositories;

import ba.sum.fpmoz.salon.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository <Car, Long> {}