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
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.service.BountyHunterService;

@RestController
@RequestMapping("/api/bounty-hunter/v1")
@Tag(name = "Bounty Hunter", description = "Endpoints para a entidade Bounty Hunter")
@AllArgsConstructor
public class BountyHunterController {

    private final BountyHunterService bountyHunterService;

    @GetMapping
    @Operation(summary = "Retrieves all Bounty Hunters", description = "Este endpoint paginado retorna todos os Bounty Hunters. " +
            "\nPara o número da página, use o requestParam page" +
            "\nPara o tamanho da página, use o requestParam size" +
            "\nPara alterar a ordem dos resultados, use o requestParam sort, que pode ser \"asc\" ou \"desc\"",
            tags = {"Bounty Hunter"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = BountyHunter.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Aqui podemos colocar qualquer descrição para o retorno", responseCode = "418", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<PagedModel<EntityModel<BountyHunter>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort

    ){
        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC :
                Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        return ResponseEntity.ok(bountyHunterService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Retrieves a Bounty Hunter by its id", description = "Com este endpoint, podemos retornar um Bounty Hunter com o seu Id",
            tags = {"Bounty Hunter"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = BountyHunter.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyHunter findById(
            @PathVariable Long id
    ){
        return bountyHunterService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Creates a Bounty Hunter", description = "Bounty Hunter",
            tags = {"Bounty Hunter"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyHunter createBountyHunter(
            @RequestBody BountyHunter bountyHunter
    ){
        return bountyHunterService.createBountyHunter(bountyHunter);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Updates a bounty hunter", description = "Updates a bounty hunter",
            tags = {"Bounty Hunter"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyHunter updateBountyHunter(
            @PathVariable Long id,
            @RequestBody BountyHunter bountyHunter
    ){
        return bountyHunterService.updateBountyHunter(bountyHunter, id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a bounty hunter", description = "Deletes a bounty hunter",
            tags = {"Bounty Hunter"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> deleteBountyHunter(
            @PathVariable Long id
    ){
        bountyHunterService.deleteBountyHunterById(id);
        return ResponseEntity.noContent().build();
    }
    
}
