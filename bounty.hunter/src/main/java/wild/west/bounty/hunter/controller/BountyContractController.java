package wild.west.bounty.hunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.model.BountyContract;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.request.BountyContractRequest;
import wild.west.bounty.hunter.service.BountyContractService;

@RestController
@RequestMapping("/api/bounty-contract/v1")
@Tag(name = "Bounty Contract", description = "Api responsável pelo cartaz de procurado")
@AllArgsConstructor
public class BountyContractController {

    private final BountyContractService service;

    @PostMapping
    @Operation(summary = "Creates a Bounty Contract", description = "Cria um cartaz de procurado",
            tags = {"Bounty Contract"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyContract createAContract(BountyContractRequest contractRequest){
        return service.createContract(contractRequest);
    }

    @GetMapping("/{outlawName}")
    @Operation(summary = "Retrieves a bounty contract, searching by the outlaw name", description = "Este endpoint paginado retorna todos os Bounty Hunters. " +
            "\nPara o número da página, use o requestParam page" +
            "\nPara o tamanho da página, use o requestParam size" +
            "\nPara alterar a ordem dos resultados, use o requestParam sort, que pode ser \"asc\" ou \"desc\"",
            tags = {"Bounty Contract"},
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
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    public Page<BountyContract> searchBountyContract(
            @PathVariable String outlawName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort){

        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC :
                Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "cowboyName"));

        return service.findContractByOutlaw(outlawName, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Bounty Contract", description = "Atualiza um cartaz de procurado",
            tags = {"Bounty Contract"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyContract updateContract(
            @PathVariable Long id,
            @RequestBody BountyContractRequest request){
        return service.udpateBountyContract(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Bounty Contract", description = "Apaga um cartaz de procurado",
            tags = {"Bounty Contract"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public BountyContract deleteContract(@PathVariable Long id){
        return service.deleteBountyContract(id);
    }
}
