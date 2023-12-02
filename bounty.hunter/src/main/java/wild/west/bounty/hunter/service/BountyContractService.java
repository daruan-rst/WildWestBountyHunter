package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.BountyContractController;
import wild.west.bounty.hunter.exceptions.NotOutlawException;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.*;
import wild.west.bounty.hunter.repositories.BountyContractRepository;
import wild.west.bounty.hunter.repositories.PersonRepository;
import wild.west.bounty.hunter.repositories.TownRepository;
import wild.west.bounty.hunter.request.BountyContractRequest;

import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log
@AllArgsConstructor
@Service
public class BountyContractService {

    private final BountyContractRepository repository;

    private final PersonRepository personRepository;

    private final TownRepository townRepository;

    public BountyContract createContract(BountyContractRequest contractRequest){
        Town lastTown  = townRepository.findByTownName(contractRequest.lastTown())
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a town by the name of: %s",
                                        contractRequest.lastTown())));

        Person person = personRepository.findByNameAndObjectType(contractRequest.outlawName(), "OUTLAW")
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a person by the name of: %s",
                        contractRequest.outlawName())));

        if (!(person instanceof Outlaw)){
            throw new NotOutlawException(person);
        }

        BountyContract contract = new BountyContract();
        contract.setOutlaw((Outlaw) person);
        contract.setOutlawDescription(contractRequest.description());
        contract.setReward(contractRequest.reward());
        contract.setLastPlace(lastTown);
        contract.setPosterName("WANTED! DEAD OR ALIVE");

        return repository.save(contract);
    }

    public Page<BountyContract> findContractByOutlaw(String outlawName, Pageable page){
        log.info(String.format("Searching a contract by the the name of: %s", outlawName));

        Page<BountyContract> contracts = repository.findBountyContractsByOutlaw(outlawName, page);

        if (contracts.isEmpty()){
            throw new NoSuchElementException();
        }
        return contracts;
        }

    public BountyContract udpateBountyContract(long id, BountyContract newBountyContract){
        log.info("Updating a BountyContract");
        BountyContract bountyContract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyContract found for this id"));
//        bountyContract.setBountyContractName(newBountyContract.getBountyContractName());
        bountyContract.setLastPlace(newBountyContract.getLastPlace());
        bountyContract.setPosterName(newBountyContract.getPosterName());
        bountyContract.setReward(newBountyContract.getReward());
        bountyContract.setOutlaw(newBountyContract.getOutlaw());
        bountyContract.setOutlawDescription(newBountyContract.getOutlawDescription());
        repository.save(bountyContract);
        return bountyContract;
    }
    

    public BountyContract deleteBountyContract(long id){
        log.info("Deleting a BountyContract by id");
        BountyContract bountyContract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyContract found for this id"));
//        bountyContract.removeLinks();
//        bountyContract.add(linkTo(methodOn(BountyContractController.class).deleteBountyContract(id)).withSelfRel());
        repository.delete(bountyContract);
        return bountyContract;
    }

}
