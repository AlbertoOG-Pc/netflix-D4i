package com.everis.d4i.tutorial.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@ControllerAdvice
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NetflixRestExceptionHandler {

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public NetflixResponse unhandledErrors(HttpServletRequest req, Exception ex) {
		return new NetflixResponse(ExceptionConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
	}

	@ExceptionHandler({ NetflixException.class })
	@ResponseBody
	public NetflixResponse handleLmException(final HttpServletRequest request, final HttpServletResponse response,
			final NetflixException ex) {
		response.setStatus(ex.getCode());
		return new NetflixResponse(ExceptionConstants.ERROR, String.valueOf(ex.getCode()), ex.getMessage(),
				ex.getErrorList());
	}
}
