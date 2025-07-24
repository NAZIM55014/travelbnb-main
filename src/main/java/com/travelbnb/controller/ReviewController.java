package com.travelbnb.controller;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import com.travelbnb.entity.Reviews;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.repository.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview")
    public ResponseEntity<Reviews> addReview(
          @AuthenticationPrincipal AppUser user,
          @RequestParam long propertyId,
          @RequestBody Reviews review
    ){
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        review.setAppUser(user);
        review.setProperty(property);
        reviewsRepository.save(review);
        return new ResponseEntity<>(review, HttpStatus.OK);

    }
}
