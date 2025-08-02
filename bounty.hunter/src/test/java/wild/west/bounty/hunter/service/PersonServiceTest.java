package wild.west.bounty.hunter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wild.west.bounty.hunter.model.Citizen;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}