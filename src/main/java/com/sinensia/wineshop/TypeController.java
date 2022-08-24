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
@RequestMapping("/api/type")
public class TypeController {
    @Autowired
    private TypeRepository typeRepository;

    @GetMapping("/{id}")
    Type one(@PathVariable Integer id) throws Exception {
        return typeRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
    }

    @GetMapping("/hal/{id}")
    EntityModel<Type> hal_one(@PathVariable Integer id) throws Exception {
        Type type = typeRepository.findById(id).orElseThrow(() -> new Exception("Not found"));
        return EntityModel.of(type,
                linkTo(methodOn(TypeController.class).hal_one(id)).withSelfRel()
        );

    }
}
