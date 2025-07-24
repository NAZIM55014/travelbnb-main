package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public AppUser createUser(AppUser user) {

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        AppUser createdUser = appUserRepository.save(user);
        return createdUser;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUserName = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUserName.isPresent()){
            AppUser appUser = opUserName.get();
            if(BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                String token = jwtService.generateToken(appUser);
                return token;
            }
        }
        return null;
    }
}
