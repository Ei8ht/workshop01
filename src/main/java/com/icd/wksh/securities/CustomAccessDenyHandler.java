package com.icd.wksh.securities;

import com.google.gson.Gson;
import com.icd.wksh.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDenyHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomAccessDenyHandler.class);

    @Autowired
    private Gson gson;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String errorMessage = httpServletRequest.getRequestURI()+" not allow to access by "+httpServletRequest.getRemoteAddr();
        log.error(errorMessage);
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, You're not allow to access this resource.");

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String body = gson.toJson(Response.fail(errorMessage));
        httpServletResponse.getOutputStream().write(body.getBytes());
    }
}
