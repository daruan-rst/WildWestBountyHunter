package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.SaloonController;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Saloon;
import wild.west.bounty.hunter.model.Town;
import wild.west.bounty.hunter.repositories.SaloonRepository;
import wild.west.bounty.hunter.repositories.TownRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log
@Service
@AllArgsConstructor
public class SaloonService {

    private final SaloonRepository repository;

    private final TownRepository townRepository;


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

    public Saloon udpateSaloon(long id, Saloon newSaloon){
        Saloon saloon = this.findById(id);
        saloon.setSaloonName(newSaloon.getSaloonName());
        saloon.setTown(newSaloon.getTown());
        repository.save(saloon);
        return saloon;
    }

    public Saloon addToTown(long saloonId, long townId){
        Saloon saloon = this.findById(saloonId);
        Town town = townRepository.findById(townId).orElseThrow(() -> new ResourceNotFoundException("No town found for this id"));
        saloon.setTown(town);
        town.getSaloons().add(saloon);
        return saloon;
    }

    public Saloon deleteSaloon(long id){
        log.info("Deleting a saloon by id");
        Saloon saloon = this.findById(id);
        saloon.removeLinks();
        saloon.add(linkTo(methodOn(SaloonController.class).deleteSaloon(id)).withSelfRel());
        repository.delete(saloon);
        return saloon;
    }


}
