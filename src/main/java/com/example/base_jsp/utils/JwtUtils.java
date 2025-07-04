package com.example.base_jsp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    private static String[] split(String jwt) {
        return jwt.split("\\.");
    }

    public static JsonObject toJson(String jwt) throws InvalidParameterException {
        String[] data = split(jwt);
        if (data.length != 3) {
            throw new InvalidParameterException("JWT is invalid");
        } else {
            String json = new String(Base64.getUrlDecoder().decode(data[1]));
            return new Gson().fromJson(json, JsonObject.class);
        }
    }
}
