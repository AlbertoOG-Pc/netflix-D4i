package com.everis.d4i.tutorial.exceptions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(
        		"{ \"status\": \"" + CommonConstants.SUCCESS + "\","+
        		" \"code\": \"" + HttpStatus.UNAUTHORIZED.value() + "\","+
        		"\"message\": \"" + ExceptionConstants.UNAUTHORIZED_ERROR + "\","+
        		" \"data\": \"null\" }");
	}

}
