package wild.west.bounty.hunter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
