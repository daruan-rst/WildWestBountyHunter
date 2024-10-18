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
import wild.west.bounty.hunter.model.Equipment;
import wild.west.bounty.hunter.model.Person;
import wild.west.bounty.hunter.response.MurderResponse;
import wild.west.bounty.hunter.service.PersonService;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Person", description = "Endpoints para a entidade Person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @Operation(summary = "Retrieves all People", description = "Este endpoint paginado retorna todos os People. " +
            "\nPara o número da página, use o requestParam page" +
            "\nPara o tamanho da página, use o requestParam size" +
            "\nPara alterar a ordem dos resultados, use o requestParam sort, que pode ser \"asc\" ou \"desc\"",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Person.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Aqui podemos colocar qualquer descrição para o retorno", responseCode = "418", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<Person>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort

    ){
        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC :
                Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Retrieves a Person by its id", description = "Com este endpoint, podemos retornar uma Person com o seu Id",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Person.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Person findById(
            @PathVariable Long id
    ){
        return personService.findById(id);
    }

    @GetMapping(value = "/find-by-job/{job}")
    @Operation(summary = "Retrieves a Person by its job", description = "Com este endpoint, podemos retornar uma Person com o seu Id",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Person.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<Person>>> findByType(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort,
            String objectType

    ){
        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC :
                Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        return ResponseEntity.ok(personService.findByPersonType(objectType, pageable));
    }

    @PostMapping
    @Operation(summary = "Creates a Person", description = "Person",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Person createPerson(
            @RequestBody Person person
    ){
        return personService.createPerson(person);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Updates a person", description = "Updates a person",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Person updatePerson(
            @PathVariable Long id,
            @RequestBody Person person
    ){
        return personService.updatePerson(person, id);
    }

    @PutMapping(value = "kill/{killerId}/{victimId}")
    @Operation(summary = "Lets a person kill another", description = "Effectively, changes alive of a victim to false. " +
            "The killer should be cast to an Outlaw",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public MurderResponse killSomebody(
            @PathVariable Long killerId,
            @PathVariable Long victimId
    ){
        return personService.killSomebody(killerId, victimId);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Updates a person", description = "Updates a person",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Person addEquipment(
            @PathVariable Long id,
            @RequestBody Equipment equipment
    ){
        return personService.addEquipment(equipment, id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a person", description = "Deletes a person",
            tags = {"Person"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> deletePerson(
            @PathVariable Long id
    ){
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }
    
}
