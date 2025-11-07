package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.controller.dto.request.EquipmentRequest;
import wild.west.bounty.hunter.model.Equipment;
import wild.west.bounty.hunter.service.EquipmentService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/equipment/v1")
@Tag(name = "Equipment", description = "Operations related to equipment management")
public class EquipmentController {

    private EquipmentService service;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get equipment by ID",
            description = "Retrieves a specific equipment entity using its unique identifier.",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipment successfully retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Equipment.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Equipment findEquipmentById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create new equipment",
            description = "Creates a new equipment entry in the system. The request body must contain valid equipment details.",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Equipment successfully created",
                            content = @Content(schema = @Schema(implementation = Equipment.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Equipment createAnEquipment(@RequestBody EquipmentRequest equipmentRequest) {
        return service.createEquipment(equipmentRequest);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update existing equipment",
            description = "Updates an existing equipment record identified by its ID. All fields in the request body will replace existing values.",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipment successfully updated",
                            content = @Content(schema = @Schema(implementation = Equipment.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Equipment updateAnEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        return service.updateEquipment(id, equipment);
    }

    @PatchMapping("/equipment/{equipmentId}/person/{personId}")
    @Operation(
            summary = "Assign equipment to a person",
            description = "Associates an existing equipment with a specific person, effectively assigning it to them.",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipment successfully assigned",
                            content = @Content(schema = @Schema(implementation = Equipment.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid ID provided", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Equipment or person not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Equipment assignEquipmentToPerson(@PathVariable Long equipmentId, @PathVariable Long personId) {
        return service.addingAnEquipmentToPerson(personId, equipmentId);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete equipment",
            description = "Removes an equipment record permanently from the system based on its ID.",
            tags = {"Equipment"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Equipment successfully deleted",
                            content = @Content(schema = @Schema(implementation = Equipment.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Equipment not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
            }
    )
    public Equipment deleteEquipment(@PathVariable Long id) {
        return service.deleteEquipment(id);
    }
}
