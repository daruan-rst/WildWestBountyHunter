package wild.west.bounty.hunter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wild.west.bounty.hunter.model.*;
import wild.west.bounty.hunter.repositories.PersonRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
        verify(personRepository, times(1)).save(someone);
        assertNotNull(citizen.getLinks());
        assertTrue(citizen.getLinks().hasLink("self"));
    }

    @Test
    void createBountyHunter_shouldSetAliveToTrue() {
        someone = new BountyHunter();
        someone.setId(1L);
        someone.setName("Joanne");
        someone.setAlive(false);

        BountyHunter hunter = (BountyHunter) someone;

        hunter.setReputation(CRUEL);

        when(personRepository.save(any(Person.class))).thenReturn(hunter);

        // Act
        Person citizen = personService.createPerson(hunter);

        // Assert
        assertTrue(citizen.isAlive());
        assertInstanceOf(BountyHunter.class, citizen);
        BountyHunter thisBountyHunter = (BountyHunter) citizen;
        assertEquals(CRUEL, thisBountyHunter.getReputation());
        verify(personRepository, times(1)).save(hunter);
        assertNotNull(citizen.getLinks());
        assertTrue(citizen.getLinks().hasLink("self"));
    }

    @Test
    void createOutlaw_shouldSetAliveToTrue() {
        someone = new Outlaw();
        someone.setId(1L);
        someone.setName("John Doe");
        someone.setAlive(false);

        Outlaw outlaw = (Outlaw) someone;

        outlaw.setBountyValue(BigDecimal.valueOf(10000));

        when(personRepository.save(any(Person.class))).thenReturn(outlaw);

        // Act
        Person citizen = personService.createPerson(outlaw);

        // Assert
        assertTrue(citizen.isAlive());
        assertInstanceOf(Outlaw.class, citizen);
        Outlaw thisOutlaw = (Outlaw) citizen;
        assertEquals(BigDecimal.valueOf(10000), thisOutlaw.getBountyValue());
        verify(personRepository, times(1)).save(outlaw);
        assertNotNull(citizen.getLinks());
        assertTrue(citizen.getLinks().hasLink("self"));
    }

    @Test
    void createSheriff_shouldSetAliveToTrue() {
        someone = new Sheriff();
        someone.setId(1L);
        someone.setName("Joan of Arc");
        someone.setAlive(false);

        when(personRepository.save(any(Person.class))).thenReturn(someone);

        // Act
        Person citizen = personService.createPerson(someone);

        // Assert
        assertTrue(citizen.isAlive());
        assertInstanceOf(Sheriff.class, citizen);
        verify(personRepository, times(1)).save(someone);
        assertNotNull(citizen.getLinks());
        assertTrue(citizen.getLinks().hasLink("self"));
    }

    @Test
    void createPerson_shouldHandleNullInput() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> personService.createPerson(null));
    }

}