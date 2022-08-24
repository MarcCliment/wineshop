package com.sinensia.wineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/Region")
public class RegionController {
    @Autowired
    private RegionRepository RegionRepository;

    @GetMapping("/{id}")
    Region one(@PathVariable Integer id) throws Exception {
        return RegionRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
    }

    @GetMapping("/hal/{id}")
    EntityModel<Region> hal_one(@PathVariable Integer id) throws Exception {
        Region Region = RegionRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        return EntityModel.of(Region,
                linkTo(methodOn(RegionController.class).hal_one(id)).withSelfRel()
        );

    }
}
