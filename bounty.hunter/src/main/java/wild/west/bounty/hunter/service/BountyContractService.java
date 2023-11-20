package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.exceptions.NotOutlawException;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.BountyContract;
import wild.west.bounty.hunter.model.Outlaw;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.model.Town;
import wild.west.bounty.hunter.repositories.BountyContractRepository;
import wild.west.bounty.hunter.repositories.PersonRepository;
import wild.west.bounty.hunter.repositories.TownRepository;
import wild.west.bounty.hunter.request.BountyContractRequest;

import java.util.NoSuchElementException;
import java.util.Optional;

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
                                        contractRequest.getLastTown())));

        Person person = personRepository.findByNameAndObjectType(contractRequest.outlawName(), "OUTLAW")
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a person by the name of: %s",
                        contractRequest.getOutlawName())));

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

    public Page<BountyContract> findContractByOutlaw(String outlawName){
        log.info(String.format("Searching a contract by the the name of: %s", outlawName));

        Page<BountyContract> contracts = repository.findBountyContractsByOutlaw(outlawName);

        if (contracts.isEmpty()){
            throw new NoSuchElementException();
        }
        return contracts;
        }

}
