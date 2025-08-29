package wild.west.bounty.hunter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import wild.west.bounty.hunter.model.*;
import wild.west.bounty.hunter.repositories.PersonRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static wild.west.bounty.hunter.model.enums.Reputation.CRUEL;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PagedResourcesAssembler<Person> assembler;

    @InjectMocks
    private PersonService personService;

    private Person someone;
    private Pageable pageable;
    private Page<Person> personPage;
    private List<Person> personList;
    private PagedModel<EntityModel<Person>> pagedModel;

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

    @Test
    void findAll_shouldReturnPagedModel() {
        // Arrange

        Person person1 = new Outlaw();
        person1.setId(1L);
        person1.setName("Win Butler");

        Person person2 = new Sheriff();
        person2.setId(2L);
        person2.setName("Régine Chassagne");

        personList = Arrays.asList(person1, person2);
        pageable = PageRequest.of(0, 10, Sort.by("name"));
        personPage = new PageImpl<>(personList, pageable, personList.size());

        // Mock paged model
        pagedModel = PagedModel.of(
                Arrays.asList(EntityModel.of(person1), EntityModel.of(person2)),
                new PagedModel.PageMetadata(10, 0, 2));


        when(personRepository.findAll(any(Pageable.class))).thenReturn(personPage);
        when(assembler.toModel(eq(personPage), any(Link.class))).thenReturn(pagedModel);

        // Act
        PagedModel<EntityModel<Person>> result = personService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(personRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAll_shouldHandleEmptyResults() {
        // Arrange
        pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Person> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        PagedModel<EntityModel<Person>> emptyPagedModel = PagedModel.of(
                List.of(),
                new PagedModel.PageMetadata(10, 0, 0)
        );

        when(personRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);
        when(assembler.toModel(eq(emptyPage), any(Link.class))).thenReturn(emptyPagedModel);

        // Act
        PagedModel<EntityModel<Person>> result = personService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void findByPersonType_shouldReturnPagedModelForValidType() {

        Person outlaw = new Outlaw();
        outlaw.setId(1L);
        outlaw.setName("Win Butler");

        Person sheriff = new Sheriff();
        sheriff.setId(2L);
        sheriff.setName("Régine Chassagne");

        personList = Arrays.asList(outlaw, sheriff);
        pageable = PageRequest.of(0, 10, Sort.by("name"));
        personPage = new PageImpl<>(personList, pageable, personList.size());

        // Mock paged model
        pagedModel = PagedModel.of(
                Arrays.asList(EntityModel.of(outlaw), EntityModel.of(sheriff)),
                new PagedModel.PageMetadata(10, 0, 2)
        );

        // Arrange
        String personType = "OUTLAW";
        when(personRepository.findPersonByObjectType(eq(personType), any(Pageable.class)))
                .thenReturn(personPage);
        when(assembler.toModel(eq(personPage), any(Link.class)))
                .thenReturn(pagedModel);

        // Act
        PagedModel<EntityModel<Person>> result = personService.findByPersonType(personType, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(personRepository, times(1)).findPersonByObjectType(personType, pageable);
        result.getContent().forEach(entityModel -> {
            Person person = entityModel.getContent();
            assertTrue(person.getLinks().hasLink("self"));
        });
    }

    @Test
    void findByPersonType_shouldHandleEmptyResults() {
        // Arrange

        Person outlaw = new Outlaw();
        outlaw.setId(1L);
        outlaw.setName("Win Butler");

        Person sheriff = new Sheriff();
        sheriff.setId(2L);
        sheriff.setName("Régine Chassagne");

        personList = Arrays.asList(outlaw, sheriff);
        pageable = PageRequest.of(0, 10, Sort.by("name"));
        personPage = new PageImpl<>(personList, pageable, personList.size());


        String personType = "ASTRONAUT";
        Page<Person> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        PagedModel<EntityModel<Person>> emptyPagedModel = PagedModel.of(
                List.of(),
                new PagedModel.PageMetadata(10, 0, 0)
        );

        when(personRepository.findPersonByObjectType(eq(personType), any(Pageable.class)))
                .thenReturn(emptyPage);
        when(assembler.toModel(eq(emptyPage), any(Link.class)))
                .thenReturn(emptyPagedModel);

        // Act
        PagedModel<EntityModel<Person>> result = personService.findByPersonType(personType, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        result.getContent().forEach(entityModel -> {
            Person person = entityModel.getContent();
            assertTrue(person.getLinks().hasLink("self"));
        });
    }

    @Test
    void findByPersonType_shouldHandleInvalidPersonType() {
        // Arrange
        pageable = PageRequest.of(0, 10, Sort.by("name"));
        String invalidType = "INVALID_TYPE";
        Page<Person> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        PagedModel<EntityModel<Person>> emptyPagedModel = PagedModel.of(
                List.of(),
                new PagedModel.PageMetadata(10, 0, 0)
        );

        when(personRepository.findPersonByObjectType(eq(invalidType), any(Pageable.class)))
                .thenReturn(emptyPage);
        when(assembler.toModel(eq(emptyPage), any(Link.class)))
                .thenReturn(emptyPagedModel);

        // Act
        PagedModel<EntityModel<Person>> result = personService.findByPersonType(invalidType, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        result.getContent().forEach(entityModel -> {
            Person person = entityModel.getContent();
            assertTrue(person.getLinks().hasLink("self"));
        });
    }

    @Test
    void updatePerson(){
        someone = new BountyHunter();
        someone.setId(1L);
        someone.setName("Joanne");
        someone.setAlive(false);

        BountyHunter hunter = (BountyHunter) someone;

        hunter.setReputation(CRUEL);

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(hunter));

        BountyHunter newHunter = hunter;
        newHunter.setName("Diamond Heart");
        when(personRepository.save(any(Person.class))).thenReturn(newHunter);

        // Act
        Person citizen = personService.updatePerson(hunter, hunter.getId());

        // Assert
        assertFalse(citizen.isAlive());
        assertInstanceOf(BountyHunter.class, citizen);
        BountyHunter thisBountyHunter = (BountyHunter) citizen;
        assertEquals(CRUEL, thisBountyHunter.getReputation());
        verify(personRepository, times(1)).findById(hunter.getId());
        verify(personRepository, times(1)).save(newHunter);
        assertNotNull(citizen.getLinks());
        assertTrue(citizen.getLinks().hasLink("self"));
    }



}