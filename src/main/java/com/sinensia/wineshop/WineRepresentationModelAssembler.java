package com.sinensia.wineshop;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class WineRepresentationModelAssembler implements SimpleRepresentationModelAssembler<Wine> {

    @Override
    public void addLinks(EntityModel<Wine> resource) {
        try {
            resource.add(linkTo(methodOn(WineController.class).one(resource.getContent().getId())).withSelfRel())
                    .add(linkTo(methodOn(WineryController.class).one(resource.getContent().getWinery().getId())).withRel("winery"))
                    .add(linkTo(methodOn(RegionController.class).one(resource.getContent().getRegion().getId())).withRel("region"))
                    .add(linkTo(methodOn(TypeController.class).one(resource.getContent().getType().getId())).withRel("type"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Wine>> resources) {
        // ...
    }
}
