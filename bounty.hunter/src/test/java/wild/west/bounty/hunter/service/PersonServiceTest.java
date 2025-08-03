package wild.west.bounty.hunter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.model.Citizen;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static wild.west.bounty.hunter.model.enums.Reputation.CRUEL;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person someone;

    @Test
    void createCitizen_shouldSetAliveToTrue() {
        someone = new Citizen();
        someone.setId(1L);
        someone.setName("John Wayne");
        someone.setAlive(false);
        when(personRepository.save(any(Person.class))).thenReturn(someone);

        // Act
        Person citizen = personService.createPerson(someone);

        // Assert
        assertTrue(citizen.isAlive());
        assertInstanceOf(Citizen.class, citizen);
    }

    @Test
    void createBountyHunter_shouldSetAliveToTrue() {
        someone = new BountyHunter();
        someone.setId(1L);
        someone.setName("John Wayne");
        someone.setAlive(false);

        BountyHunter hunter = (BountyHunter) someone;

        hunter.setReputation(CRUEL);

        when(personRepository.save(any(Person.class))).thenReturn(hunter);

        // Act
        Person citizen = personService.createPerson(hunter);

        // Assert
        assertTrue(citizen.isAlive());
        assertInstanceOf(BountyHunter.class, citizen);
    }
}