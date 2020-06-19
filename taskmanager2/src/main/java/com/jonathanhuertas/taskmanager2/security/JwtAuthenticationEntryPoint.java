package com.jonathanhuertas.taskmanager2.security;



import com.google.gson.Gson;
import com.jonathanhuertas.taskmanager2.exceptions.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
AuthenticationEntryPoint is an interface that provides the implementation for a method called commence
which is called when an exception is thrown when a user is trying to access a resource that requires authentication

 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {


        InvalidLoginResponse loginResponse = new InvalidLoginResponse();

        String jsonLoginResponse = new Gson().toJson(loginResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);

    }
}
