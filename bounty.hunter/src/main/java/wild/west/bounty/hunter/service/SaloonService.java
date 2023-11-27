package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.SaloonController;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Saloon;
import wild.west.bounty.hunter.repositories.SaloonRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log
@Service
@AllArgsConstructor
public class SaloonService {

    private final SaloonRepository repository;


    public Saloon findById(Long id){
        log.info("Finding a saloon by id");
        Saloon saloon = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No saloon found for this id"));
        saloon.add(linkTo(methodOn(SaloonController.class).findSaloonById(saloon.getId())).withSelfRel());
        return saloon;
    }
    public Saloon createSaloon(Saloon saloon){
        log.info("Creating a saloon");
        saloon = repository.save(saloon);
        saloon.add(linkTo(methodOn(SaloonController.class).createASaloon(saloon)).withSelfRel());
        return saloon;
    }


}
