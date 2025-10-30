package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.PersonController;
import wild.west.bounty.hunter.controller.dto.PersonRequest;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.*;
import wild.west.bounty.hunter.repositories.PersonRepository;
import wild.west.bounty.hunter.response.MurderResponse;
import wild.west.bounty.hunter.util.PersonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static wild.west.bounty.hunter.functions.person.PersonFunctions.mapPerson;

@Service
@Log
@AllArgsConstructor
public class PersonService {
    
    private final PersonRepository personRepository;

    private final PagedResourcesAssembler<Person> assembler;

    public PagedModel<EntityModel<Person>> findAll(Pageable pageable) {

        Page<Person> people = personRepository.findAll(pageable);

        people.forEach(h -> h.add(linkTo(methodOn(PersonController.class).findById(h.getId())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(people, link);
    }

    public PagedModel<EntityModel<Person>> findByPersonType(String objectType, Pageable pageable){
        Page<Person> people = personRepository.findPersonByObjectType(objectType, pageable);

        people.forEach(h -> h.add(linkTo(methodOn(PersonController.class).findById(h.getId())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(people, link);

    }

    public Person findById(Long id){
        log.info(String.format("Searching person by id: %s", id));
        Person person  = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No person found for this ID"));
        person.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
        return person;
    }

    public Person createACitizen(PersonRequest person){
        log.info("Creating a Citizen");
        Person citizen = createPerson(person, Citizen.class);

        citizen.add(linkTo(methodOn(PersonController.class).createACitizen(person)).withSelfRel());

        return citizen;
    }

    public Person createAnOutlaw(PersonRequest person) {
        log.info("Creating an Outlaw");
        Person outlaw = createPerson(person, Outlaw.class);

        outlaw.add(linkTo(methodOn(PersonController.class).createAnOutlaw(person)).withSelfRel());

        return outlaw;
    }

    public Person createABountyHunter(PersonRequest person) {
        log.info("Creating a Bounty Hunter");
        Person bountyHunter = createPerson(person, BountyHunter.class);

        bountyHunter.add(linkTo(methodOn(PersonController.class).createABountyHunter(person)).withSelfRel());

        return bountyHunter;
    }

    public Person createASheriff(PersonRequest person) {
        log.info("Creating a Sheriff");
        Person sheriff = createPerson(person, Sheriff.class);

        sheriff.add(linkTo(methodOn(PersonController.class).createASheriff(person)).withSelfRel());

        return sheriff;
    }

    private Person createPerson(PersonRequest personRequest, Class<? extends Person> personType){

        Person person = mapPerson.createPerson(personRequest, personType);
        if (!person.getEquipments().isEmpty()){
            Person finalPerson = person;
            person.getEquipments().forEach(e -> e.setPerson(finalPerson));
        }
        person = personRepository.save(person);
        return person;
    }

    public Person updatePerson(Person oldPerson, Long id){
        log.info("Updating person");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No person found for this ID"));
        person.setOrigin(oldPerson.getOrigin());
        person.setOrigin(oldPerson.getOrigin());
        person = personRepository.save(person);
        person.add(linkTo(methodOn(PersonController.class).updatePerson(id, person)).withSelfRel());
        return person;
    }

    public Person addEquipment(Equipment equipment, Long id){
        Person person = findById(id);

        log.info("Adding an equipment to a person");

        List<Equipment> equipments = person.getEquipments();

        if (equipments == null){
            equipments = new ArrayList<>();
            person.setEquipments(equipments);
        }

        equipment.setPerson(person);
        equipments.add(equipment);

        return personRepository.save(person);
    }

    public void deletePersonById(Long id){
        log.info("Deleting person by id");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No person found for this ID"));
        person.add(linkTo(methodOn(PersonController.class).deletePerson(id)).withSelfRel());
        personRepository.deleteById(id);
    }

    public MurderResponse killSomebody(long killerId, long victimId){
        Person victim = findById(victimId);
        PersonUtils.validateIfAlive(victim);

        Person killer = findById(killerId);
        PersonUtils.validateKiller(killer);

        victim.setAlive(false);

        BigDecimal amountToBeAddedToBounty = calculateBounty(victim);
        if (killer instanceof Outlaw){
            ((Outlaw) killer).setBountyValue(((Outlaw) killer).getBountyValue().add(amountToBeAddedToBounty));
        }else{
            killer = ((Outlaw) killer);
            ((Outlaw) killer).setBountyValue(amountToBeAddedToBounty);
        }

        personRepository.save(killer);
        personRepository.save(victim);
        return new MurderResponse(killer, victim);
    }

    private BigDecimal calculateBounty(Person victim){
        return victim.getMoney()
                .add(
                    victim.getEquipments().isEmpty() ?
                    BigDecimal.ZERO :
                    victim.getMoney().add(victim.getEquipments()
                    .stream()
                    .map(eq -> eq.getValue()).toList()
                        .stream()
                        .reduce( (a,b) -> BigDecimal.ZERO.add(b))
                        .get())
                );
    }


}
