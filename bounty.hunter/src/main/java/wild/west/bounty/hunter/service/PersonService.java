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
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.repositories.PersonRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Log
@AllArgsConstructor
public class PersonService {
    
    private final PersonRepository personRepository;

    private final PagedResourcesAssembler<Person> assembler;

    public PagedModel<EntityModel<Person>> findAll(Pageable pageable) {

        var people = personRepository.findAll(pageable);

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

    public Person createPerson(Person person){
        log.info("Creating person");
        person = personRepository.save(person);
        person.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
        return person;
    }

    public Person updatePerson(Person oldPerson, Long id){
        log.info("Updating person");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No person found for this ID"));
        person.setOrigin(oldPerson.getOrigin());
        person.setOrigin(oldPerson.getOrigin());
        person = personRepository.save(person);
        person.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return person;
    }

    public void deletePersonById(Long id){
        log.info("Deleting person by id");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No person found for this ID"));
        person.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        personRepository.deleteById(id);
    }
}
