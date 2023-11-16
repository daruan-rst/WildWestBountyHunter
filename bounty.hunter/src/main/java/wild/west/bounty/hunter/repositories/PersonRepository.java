package wild.west.bounty.hunter.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wild.west.bounty.hunter.model.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findPersonByObjectType(String objectType, Pageable pageable);
}
