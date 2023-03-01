package fa.training.repository;

import fa.training.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {

    Optional<Hall> findByName(String name);
}
