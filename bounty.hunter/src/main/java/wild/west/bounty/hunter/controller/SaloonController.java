package wild.west.bounty.hunter.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wild.west.bounty.hunter.model.Saloon;
import wild.west.bounty.hunter.service.SaloonService;

@AllArgsConstructor
@RestController("/api/saloon/v1")
public class SaloonController {

    private final SaloonService service;


    @GetMapping("/{id}")
    public Saloon findSaloonById(@PathVariable Long id){
        return service.findById(id);
    }
    @PostMapping
    public Saloon createASaloon(@RequestBody Saloon saloon){
        return service.createSaloon(saloon);
    }
}
