package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Town;
import wild.west.bounty.hunter.repositories.TownRepository;

import java.util.List;

@Service
@Log
@AllArgsConstructor
public class TownService {
    
    private final TownRepository townRepository;

    private final PagedResourcesAssembler<Town> assembler;

    public List<Town> findAll(Pageable pageable) {
        return townRepository.findAll();
    }

    public Town findById(Long id){
        log.info(String.format("Searching town by id: %s", id));
        Town town  = townRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No town found for this ID"));

        return town;
    }

    public Town createTown(Town town){
        log.info("Creating a new town");
        town = townRepository.save(town);

        return town;
    }

    public Town updateTown(Town newTown, Long id){
        log.info("Updating town");
        Town town = townRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No town found for this ID"));
        town.setSaloons(newTown.getSaloons());
        town.setTownName(newTown.getTownName());
        town = townRepository.save(town);

        return town;
    }

    public void deleteTownById(Long id){
        log.info("Deleting town by id");
        Town town = townRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No town found for this ID"));

        townRepository.deleteById(id);
    }
}
