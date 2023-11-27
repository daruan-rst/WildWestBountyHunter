package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Saloon;
import wild.west.bounty.hunter.repositories.SaloonRepository;

@Log
@Service
@AllArgsConstructor
public class SaloonService {

    private final SaloonRepository repository;


    public Saloon findById(Long id){
        log.info("Finding a saloon by id");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No saloon found for this id"));
    }
    public Saloon createSaloon(Saloon saloon){
        log.info("Creating a saloon");
        return repository.save(saloon);
    }


}
