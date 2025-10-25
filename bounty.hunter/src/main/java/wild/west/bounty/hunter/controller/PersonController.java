package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.controller.dto.PersonRequest;
import wild.west.bounty.hunter.model.Equipment;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.response.MurderResponse;
import wild.west.bounty.hunter.service.PersonService;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Person", description = "Operations related to person management")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @Operation(
            summary = "List all people (paginated)",
            description = """
            Returns a paginated list of all people in the system.
            - Use the `page` parameter to specify the page number.
            - Use the `size` parameter to specify the number of results per page.
            - Use the `direct` parameter to choose sorting direction (`asc` or `desc`).
            Results are sorted by the person's name by default.
            """,
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of people successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Person.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<Person>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort
    ) {
        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get person by ID",
            description = "Retrieves a single person based on their unique identifier.",
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person successfully retrieved",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Person findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @GetMapping("/find-by-job/{job}")
    @Operation(
            summary = "Find people by job",
            description = """
            Returns a paginated list of people who have the specified job title.
            You can also control pagination and sorting through query parameters.
            """,
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "People with the specified job successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Person.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Invalid job parameter", content = @Content),
                    @ApiResponse(responseCode = "404", description = "No people found with the specified job", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<Person>>> findByType(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort,
            @PathVariable String job
    ) {
        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        return ResponseEntity.ok(personService.findByPersonType(job, pageable));
    }

    @PostMapping
    @Operation(
            summary = "Create a new Citizen",
            description = "Registers a new person in the system with the provided attributes.",
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Person successfully created",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Person createACitizen(@RequestBody PersonRequest person) {
        return personService.createACitizen(person);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing person",
            description = "Updates all information of an existing person identified by their ID.",
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Person successfully updated",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.updatePerson(person, id);
    }

    @PutMapping("/kill/{killerId}/{victimId}")
    @Operation(
            summary = "Simulate a kill between two people",
            description = """
            Allows one person (the killer) to kill another (the victim).
            This sets the victim's 'alive' property to false.
            The killer must be an Outlaw for this operation to succeed.
            """,
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Murder action successfully processed",
                            content = @Content(schema = @Schema(implementation = MurderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid person IDs or business rule violation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Killer or victim not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public MurderResponse killSomebody(@PathVariable Long killerId, @PathVariable Long victimId) {
        return personService.killSomebody(killerId, victimId);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Add equipment to a person",
            description = "Adds a piece of equipment to the person's inventory based on their ID and the provided equipment data.",
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipment successfully added to person",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid person or equipment data", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Person addEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        return personService.addEquipment(equipment, id); // FIXME: it feels really dumb to pass the person id as a path variable and also in the request body; Find a way later to make this less redundant
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a person",
            description = "Removes a person from the system permanently using their ID.",
            tags = {"Person"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Person successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }
}
