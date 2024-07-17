package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.model.Equipment;
import wild.west.bounty.hunter.service.EquipmentService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/equipment/v1")
@Tag(name = "Equipment", description = "Endpoints para a entidade Equipment")
public class EquipmentController {

    private EquipmentService service;

    @GetMapping(value =  "/{id}")
    @Operation(summary = "Retrieves an Equipment by its id", description = "Com este endpoint, podemos retornar um Equipment com o seu Id",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Equipment.class))
                                    )
                            }),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Equipment findEquipmentById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }
    @PostMapping
    @Operation(summary = "Creates an Equipment", description = "Equipment",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Equipment.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Equipment createAEquipment(@RequestBody Equipment equipment){
        return service.createEquipment(equipment);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Updates an Equipment", description = "Equipment",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Equipment.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Equipment updateAnEquipment(@PathVariable Long id,
                                       @RequestBody Equipment equipment) {
        return service.updateEquipment(id, equipment);

    }

    @PatchMapping("equipment/{equipmentId}/person/{personId}")
    @Operation(summary = "Adds an Equipment to a person", description = "Equipment",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = Equipment.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Equipment addsAnEquipmentToPerson(@PathVariable Long equipmentId,
                                             @PathVariable Long personId){
        return service.addingAnEquipmentToPerson(personId, equipmentId);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes an Equipment", description = "Deletes a Equipment",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Equipment.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public Equipment deleteEquipment(@PathVariable Long id) {
        return service.deleteEquipment(id);
    }
}
