package com.travelbnb.payload;

import lombok.Data;

@Data
public class JWTTokenDto {
    private String type;
    private String token;

}
