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
import wild.west.bounty.hunter.controller.dto.request.WantedPosterRequest;

import java.math.BigDecimal;
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

    public WantedPoster createWantedPoster(WantedPosterRequest wantedPosterRequest){
        Town lastTown  = townRepository.findByTownName(wantedPosterRequest.lastTown())
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a town by the name of: %s",
                                        wantedPosterRequest.lastTown())));

        Person person = personRepository.findByNameAndObjectType(wantedPosterRequest.outlawName(), "OUTLAW")
                .orElseThrow(()->new ResourceNotFoundException(String.format("Could not find a person by the name of: %s",
                        wantedPosterRequest.outlawName())));

        if (!(person instanceof Outlaw)){
            throw new NotOutlawException(person);
        }

        WantedPoster wantedPoster = new WantedPoster();
        wantedPoster.setOutlaw((Outlaw) person);
        wantedPoster.setOutlawDescription(wantedPosterRequest.description());
        wantedPoster.setReward(((Outlaw) person).getBountyValue());
        wantedPoster.setLastPlace(lastTown);
        wantedPoster.setPosterName("WANTED! DEAD OR ALIVE");
        wantedPoster.add(linkTo(methodOn(WantedPosterController.class).createAWantedPoster(wantedPosterRequest)).withSelfRel());


        return repository.save(wantedPoster);
    }

    public Page<WantedPoster> findWantedPosterByOutlaw(String outlawName, Pageable page){
        log.info(String.format("Searching a wantedPoster by the the name of: %s", outlawName));

        Page<WantedPoster> wantedPosters = repository.findWantedPosterByOutlaw(outlawName, page);

        if (wantedPosters.isEmpty()){
            throw new NoSuchElementException();
        } else {
            wantedPosters.forEach(
                    c -> c.add(linkTo(methodOn(WantedPosterController.class).searchBountyWantedPoster(outlawName, page.getPageNumber(), page.getPageSize(), page.getSort().toString())).withSelfRel()));
        }
        return wantedPosters;
        }

    public WantedPoster udpateBountyWantedPoster(long id, WantedPosterRequest newBountyWantedPoster){

        log.info("Updating a Bounty WantedPoster");

        WantedPoster wantedPoster = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyWantedPoster found for this id"));

        BigDecimal newBounty = newBountyWantedPoster.reward().compareTo(wantedPoster.getReward()) > 0 ? newBountyWantedPoster.reward() : wantedPoster.getReward();

        Town lastTown = townRepository.findByTownName(newBountyWantedPoster.lastTown()).orElseThrow(() -> new ResourceNotFoundException("No BountyWantedPoster found for this id"));
        wantedPoster.setLastPlace(lastTown);
        wantedPoster.setReward(newBounty);
        wantedPoster.setOutlawDescription(newBountyWantedPoster.description());
        wantedPoster.add(linkTo(methodOn(WantedPosterController.class).updateWantedPoster(id, newBountyWantedPoster)).withSelfRel());
        repository.save(wantedPoster);
        return wantedPoster;
    }
    

    public WantedPoster deleteBountyWantedPoster(long id){
        log.info("Deleting a BountyWantedPoster by id");
        WantedPoster wantedPoster = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No BountyWantedPoster found for this id"));
        wantedPoster.removeLinks();
        wantedPoster.add(linkTo(methodOn(WantedPosterController.class).deleteWantedPoster(id)).withSelfRel());
        repository.delete(wantedPoster);
        return wantedPoster;
    }

}
