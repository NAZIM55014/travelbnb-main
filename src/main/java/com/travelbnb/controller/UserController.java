package com.travelbnb.controller;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.JWTTokenDto;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }


    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(
            @RequestBody AppUser user
    ){
        if(appUserRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email Exists", HttpStatus.BAD_REQUEST);
        }
        if(appUserRepository.existsByUsername(user.getUsername())){
            return new ResponseEntity<>("Username Exists", HttpStatus.BAD_REQUEST);
        }
        AppUser createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED)   ;
    }


    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) {
        String token = userService.verifyLogin(loginDto);
        if (token != null) {
            JWTTokenDto jwtToken = new JWTTokenDto();
            jwtToken.setType("JWT Token");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.OK);
        }
    }

}
