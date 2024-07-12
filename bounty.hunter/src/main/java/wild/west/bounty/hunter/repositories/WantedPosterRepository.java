package wild.west.bounty.hunter.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.WantedPoster;

@Repository
public interface WantedPosterRepository extends JpaRepository<WantedPoster, Long> {

    Page<WantedPoster> findBountyContractsByOutlaw(String outlaw, Pageable page);

}
