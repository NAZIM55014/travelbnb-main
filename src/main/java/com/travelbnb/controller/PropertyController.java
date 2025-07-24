package com.travelbnb.controller;

import com.travelbnb.entity.Property;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/search/properties")
    public ResponseEntity<List<Property>> searchProperty(@RequestParam String name){
        List<Property> properties = propertyRepository.searchProperty(name);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}
