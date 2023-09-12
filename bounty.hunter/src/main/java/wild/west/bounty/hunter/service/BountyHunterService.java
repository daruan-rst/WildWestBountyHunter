package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.BountyHunterController;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.repositories.BountyHunterRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
@Slf4j
public class BountyHunterService {

    private final BountyHunterRepository bountyHunterRepository;
    
    private final PagedResourcesAssembler<BountyHunter> assembler;


    public PagedModel<EntityModel<BountyHunter>> findAll(Pageable pageable) {
        
        var hunters = bountyHunterRepository.findAll(pageable);
        
        hunters.forEach(h -> h.add(linkTo(methodOn(BountyHunterController.class).findById(h.getId())).withSelfRel()));

        Link link = linkTo(methodOn(BountyHunterController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(hunters, link);
    }

    public BountyHunter findById(Long id){
        log.info(String.format("Searching hunter by id: %s", id));
        BountyHunter hunter  = bountyHunterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hunter found for this ID"));
        hunter.add(linkTo(methodOn(BountyHunterController.class).findById(hunter.getId())).withSelfRel());
        return hunter;
    }

    public BountyHunter createBountyHunter(BountyHunter bountyHunter){
        log.info("Creating bountyHunter");
        bountyHunter = bountyHunterRepository.save(bountyHunter);
        bountyHunter.add(linkTo(methodOn(BountyHunterController.class).findById(bountyHunter.getId())).withSelfRel());
        return bountyHunter;
    }

    public BountyHunter updateBountyHunter(BountyHunter oldBountyHunter, Long id){
        log.info("Updating bountyHunter");
        BountyHunter bountyHunter = bountyHunterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hunter found for this ID"));
        bountyHunter.setOrigin(oldBountyHunter.getOrigin());
        bountyHunter.setReputation(oldBountyHunter.getReputation());
        bountyHunter.setOrigin(oldBountyHunter.getOrigin());
        bountyHunter = bountyHunterRepository.save(bountyHunter);
        bountyHunter.add(linkTo(methodOn(BountyHunterController.class).findById(id)).withSelfRel());
        return bountyHunter;
    }

    public void deleteBountyHunterById(Long id){
        log.info("Deleting bountyHunter by id");
        BountyHunter bountyHunter = bountyHunterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hunter found for this ID"));
        bountyHunter.add(linkTo(methodOn(BountyHunterController.class).findById(id)).withSelfRel());
        bountyHunterRepository.deleteById(id);
    }


}
