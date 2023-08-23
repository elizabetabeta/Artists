package ba.sum.fpmoz.salon.repositories;

import ba.sum.fpmoz.salon.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository <Location, Long> {}