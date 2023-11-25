package wild.west.bounty.hunter.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.BountyContract;

@Repository
public interface BountyContractRepository extends JpaRepository<BountyContract, Long> {

    Page<BountyContract> findBountyContractsByOutlaw(String outlaw, Pageable page);

}
