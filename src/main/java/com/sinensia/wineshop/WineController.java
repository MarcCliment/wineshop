package com.sinensia.wineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/wine")
public class WineController {
    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private WineRepresentationModelAssembler assembler;

    @GetMapping("/")
    List<Wine> all() {
        return wineRepository.findAll();
    }

    @GetMapping("/{id}")
    Wine one(@PathVariable Integer id) throws Exception {
        return wineRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
    }

    @GetMapping("/hal/{id}")
    EntityModel<Wine> hal_one(@PathVariable Integer id) throws Exception {
        Wine wine = wineRepository.findById(id).orElseThrow(() -> new Exception("Not found"));

        return
                EntityModel.of(wine,
                        linkTo(methodOn(WineController.class).hal_one(id)).withSelfRel(),
                        linkTo(methodOn(WineryController.class).hal_one(wine.getWinery().getId())).withRel("winery")
                );
    }

    @GetMapping("/hal2/{id}")
    ResponseEntity<EntityModel<Wine>> hal2_one(@PathVariable Integer id) throws Exception {
        return wineRepository.findById(id)
                .map(wine -> assembler.toModel(wine))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
