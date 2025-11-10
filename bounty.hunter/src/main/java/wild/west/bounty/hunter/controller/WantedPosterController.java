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
import wild.west.bounty.hunter.model.WantedPoster;
import wild.west.bounty.hunter.model.BountyHunter;
import wild.west.bounty.hunter.controller.dto.request.WantedPosterRequest;
import wild.west.bounty.hunter.service.WantedPosterService;

@RestController
@RequestMapping("/api/wanted-poster/v1")
@Tag(name = "Wanted Poster", description = "Api responsável pelo cartaz de procurado")
@AllArgsConstructor
public class WantedPosterController {

    private final WantedPosterService service;

    @PostMapping
    @Operation(summary = "Creates a Wanted Poster", description = "Cria um cartaz de procurado",
            tags = {"Wanted Poster"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public WantedPoster createAWantedPoster(@RequestBody WantedPosterRequest contractRequest){
        return service.createWantedPoster(contractRequest);
    }

    @GetMapping("/{outlawName}")
    @Operation(summary = "Retrieves a bounty contract, searching by the outlaw name", description = "Este endpoint paginado retorna todos os Bounty Hunters. " +
            "\nPara o número da página, use o requestParam page" +
            "\nPara o tamanho da página, use o requestParam size" +
            "\nPara alterar a ordem dos resultados, use o requestParam sort, que pode ser \"asc\" ou \"desc\"",
            tags = {"Wanted Poster"},
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
    public Page<WantedPoster> searchBountyWantedPoster(
            @PathVariable String outlawName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direct", defaultValue = "asc") String sort){

        var sortDirection = sort.equalsIgnoreCase("DESC") ? Sort.Direction.DESC :
                Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        return service.findWantedPosterByOutlaw(outlawName, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Wanted Poster", description = "Atualiza um cartaz de procurado",
            tags = {"Wanted Poster"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public WantedPoster updateWantedPoster(
            @PathVariable Long id,
            @RequestBody WantedPosterRequest request){
        return service.udpateBountyWantedPoster(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Wanted Poster", description = "Apaga um cartaz de procurado",
            tags = {"Wanted Poster"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200",
                            content =
                            @Content(schema = @Schema(implementation = BountyHunter.class))),
                    @ApiResponse(description = "BadRequest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public WantedPoster deleteWantedPoster(@PathVariable Long id){
        return service.deleteBountyWantedPoster(id);
    }
}
