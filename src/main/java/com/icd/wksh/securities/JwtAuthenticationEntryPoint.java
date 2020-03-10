package com.icd.wksh.securities;

import com.google.gson.Gson;
import com.icd.wksh.commons.Response;
import com.icd.wksh.simple.ErrorEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Autowired
    private Gson gson;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
    	String errorMessage = httpServletRequest.getRequestURI()+" not authorized to access by "+httpServletRequest.getRemoteAddr();
    	log.error(errorMessage, e);
//    	httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not authorized to access this resource.");

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String body = gson.toJson(Response.fail(errorMessage));
        httpServletResponse.getOutputStream().write(body.getBytes());
    }
}