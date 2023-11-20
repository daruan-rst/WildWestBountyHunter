package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.model.BountyContract;
import wild.west.bounty.hunter.repositories.BountyContractRepository;

import java.util.NoSuchElementException;

@Log
@AllArgsConstructor
@Service
public class BountyContractService {

    private final BountyContractRepository repository;

    public Page<BountyContract> findContractByOutlaw(String outlawName){
        log.info(String.format("Searching a contract by the the name of: %s", outlawName));

        Page<BountyContract> contracts = repository.findBountyContractsByOutlaw(outlawName);

        if (contracts.isEmpty()){
            throw new NoSuchElementException();
        }
        return contracts;
        }

}
