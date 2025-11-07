package wild.west.bounty.hunter.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import wild.west.bounty.hunter.controller.EquipmentController;
import wild.west.bounty.hunter.controller.dto.request.EquipmentRequest;
import wild.west.bounty.hunter.exceptions.ResourceNotFoundException;
import wild.west.bounty.hunter.model.Equipment;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.repositories.EquipmentRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static wild.west.bounty.hunter.functions.equipment.EquipmentFunctions.mapEquipment;

@Service
@AllArgsConstructor
@Log
public class EquipmentService {
    
    private final EquipmentRepository repository;

    private final PersonService personService;

    public Equipment findById(Long id){
        log.info("Finding a equipment by id");
        Equipment equipment = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No equipment found for this id"));
        equipment.add(linkTo(methodOn(EquipmentController.class).findEquipmentById(equipment.getId())).withSelfRel());
        return equipment;
    }
    public Equipment createEquipment(EquipmentRequest request){
        log.info("Creating a equipment");
        Equipment equipment = mapEquipment.CreateAnEquipmentFromRequest(request);
        equipment = repository.save(equipment);
        equipment.add(linkTo(methodOn(EquipmentController.class).createAnEquipment(request)).withSelfRel());
        return equipment;
    }

    public Equipment updateEquipment(long id, Equipment newEquipment){
        log.info("Updating an equipment");
        Equipment equipment = findById(id);
        equipment.removeLinks();
        equipment.setPerson(newEquipment.getPerson());
        equipment.setValue(newEquipment.getValue());
        equipment = repository.save(equipment);
        equipment.add(linkTo(methodOn(EquipmentController.class).updateAnEquipment(id, equipment)).withSelfRel());
        return equipment;
    }

    public Equipment addingAnEquipmentToPerson(long personId, long equipmentId){
        log.info("Adding an equipment to a person");
        Person person = personService.findById(personId);
        Equipment equipment = this.findById(equipmentId);
        List<Equipment> currentInventory = person.getEquipments();
        currentInventory.add(equipment);
        person.setEquipments(currentInventory);
        equipment.setPerson(person);
        return repository.save(equipment);
    }

    public Equipment deleteEquipment(long id){
        log.info("Deleting a equipment by id");
        Equipment equipment = this.findById(id);
        equipment.removeLinks();
        equipment.add(linkTo(methodOn(EquipmentController.class).deleteEquipment(id)).withSelfRel());
        repository.delete(equipment);
        return equipment;
    }
    
}
