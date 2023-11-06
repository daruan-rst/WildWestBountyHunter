package wild.west.bounty.hunter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.Saloon;

@Repository
public interface SaloonRepository extends JpaRepository<Saloon, Long> {
}
