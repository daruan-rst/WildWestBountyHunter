package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.model.Saloon;
import wild.west.bounty.hunter.service.SaloonService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/saloon/v1")
@Tag(name = "Saloon", description = "Endpoints para a entidade Saloon")
public class SaloonController {

    private final SaloonService service;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Retrieves a Saloon by its id", description = "Com este endpoint, podemos retornar um Saloon com o seu Id",
            tags = {"Saloon"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Saloon.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Saloon findSaloonById(@PathVariable Long id){
        return service.findById(id);
    }
    @PostMapping
    @Operation(summary = "Creates a Saloon", description = "Saloon",
            tags = {"Saloon"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Saloon.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Saloon createASaloon(@RequestBody Saloon saloon){
        return service.createSaloon(saloon);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a Saloon", description = "Deletes a Saloon",
            tags = {"Saloon"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Saloon.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Saloon deleteSaloon(@PathVariable Long id) {
        return service.deleteSaloon(id);
    }
}
