package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.WantedPosterController;
import wild.west.bounty.hunter.exceptions.NotOutlawException;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.*;
import wild.west.bounty.hunter.repositories.WantedPosterRepository;
import wild.west.bounty.hunter.repositories.PersonRepository;
import wild.west.bounty.hunter.repositories.TownRepository;
import wild.west.bounty.hunter.request.WantedPosterRequest;

import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log
@AllArgsConstructor
@Service
public class WantedPosterService {

    private final WantedPosterRepository repository;

    private final PersonRepository personRepository;

    private final TownRepository townRepository;

    public WantedPoster createContract(WantedPosterRequest contractRequest){
        Town lastTown  = townRepository.findByTownName(contractRequest.lastTown())
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a town by the name of: %s",
                                        contractRequest.lastTown())));

        Person person = personRepository.findByNameAndObjectType(contractRequest.outlawName(), "OUTLAW")
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a person by the name of: %s",
                        contractRequest.outlawName())));

        if (!(person instanceof Outlaw)){
            throw new NotOutlawException(person);
        }

        WantedPoster contract = new WantedPoster();
        contract.setOutlaw((Outlaw) person);
        contract.setOutlawDescription(contractRequest.description());
        contract.setReward(contractRequest.reward());
        contract.setLastPlace(lastTown);
        contract.setPosterName("WANTED! DEAD OR ALIVE");
        contract.add(linkTo(methodOn(WantedPosterController.class).createAContract(contractRequest)).withSelfRel());


        return repository.save(contract);
    }

    public Page<WantedPoster> findContractByOutlaw(String outlawName, Pageable page){
        log.info(String.format("Searching a contract by the the name of: %s", outlawName));

        Page<WantedPoster> contracts = repository.findBountyContractsByOutlaw(outlawName, page);

        if (contracts.isEmpty()){
            throw new NoSuchElementException();
        } else {
            contracts.forEach(
                    c -> c.add(linkTo(methodOn(WantedPosterController.class).searchBountyContract(outlawName, page.getPageNumber(), page.getPageSize(), page.getSort().toString())).withSelfRel()));
        }
        return contracts;
        }

    public WantedPoster udpateBountyContract(long id, WantedPosterRequest newBountyContract){
        log.info("Updating a BountyContract");
        WantedPoster wantedPoster = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyContract found for this id"));
        Town lastTown = townRepository.findByTownName(newBountyContract.lastTown()).orElseThrow(() -> new ResourceNotFoundException("No BountyContract found for this id"));
        wantedPoster.setLastPlace(lastTown);
        wantedPoster.setReward(newBountyContract.reward());
        wantedPoster.setOutlawDescription(newBountyContract.description());
        wantedPoster.add(linkTo(methodOn(WantedPosterController.class).updateContract(id, newBountyContract)).withSelfRel());
        repository.save(wantedPoster);
        return wantedPoster;
    }
    

    public WantedPoster deleteBountyContract(long id){
        log.info("Deleting a BountyContract by id");
        WantedPoster wantedPoster = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyContract found for this id"));
        wantedPoster.removeLinks();
        wantedPoster.add(linkTo(methodOn(WantedPosterController.class).deleteContract(id)).withSelfRel());
        repository.delete(wantedPoster);
        return wantedPoster;
    }

}
