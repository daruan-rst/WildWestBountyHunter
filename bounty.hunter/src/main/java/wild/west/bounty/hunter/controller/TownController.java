package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.model.Town;
import wild.west.bounty.hunter.service.TownService;

@AllArgsConstructor
@RestController("/api/town/v1")
@Tag(name = "Town", description = "Endpoints para a entidade Town")
public class TownController {

    private TownService service;

    @GetMapping("/{id}")
    @Operation(summary = "Retrieves a Town by its id", description = "Com este endpoint, podemos retornar um Town com o seu Id",
            tags = {"Town"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Town.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Town findTownById(@PathVariable Long id){
        return service.findById(id);
    }
    @PostMapping
    @Operation(summary = "Creates a Town", description = "Town",
            tags = {"Town"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Town.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Town createATown(@RequestBody Town town){
        return service.createTown(town);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Town", description = "Town",
            tags = {"Town"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Town.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Town updateATown(@PathVariable Long id,
                            @RequestBody Town town){
        return service.updateTown(town, id);
    }

    @DeleteMapping
    @Operation(summary = "Deletes a Town", description = "Deletes a Town",
            tags = {"Town"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Town.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Town deleteTown(Long id) {
        return service.deleteTownById(id);
    }
}
