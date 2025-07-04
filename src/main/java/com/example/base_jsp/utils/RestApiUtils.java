package com.example.base_jsp.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class RestApiUtils {

    public static final String TOKEN_PREFIX = "bearer ";
    public static final String HEADER_TOKEN = "Authorization";

    public static String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_TOKEN);

        if (StringUtils.isBlank(token)) return null;

        token = token.replaceFirst("(?i)" + TOKEN_PREFIX, "");

        return token;
    }

}
