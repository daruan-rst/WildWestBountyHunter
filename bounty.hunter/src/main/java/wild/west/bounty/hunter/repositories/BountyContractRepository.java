package wild.west.bounty.hunter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.BountyContract;

@Repository
public interface BountyContractRepository extends JpaRepository<BountyContract, Long> {
}
