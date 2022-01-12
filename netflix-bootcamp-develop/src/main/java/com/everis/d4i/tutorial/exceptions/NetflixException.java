package com.everis.d4i.tutorial.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.everis.d4i.tutorial.dto.ErrorDto;

public abstract class NetflixException extends Exception {

	private static final long serialVersionUID = -7482635401716007171L;

	private final int code;

	private final List<ErrorDto> errorList = new ArrayList<>();

	public NetflixException(final int code, final String message) {
		super(message);
		this.code = code;
	}

	public NetflixException(final int code, final String message, final List<ErrorDto> errorList) {
		super(message);
		this.code = code;
		this.errorList.addAll(errorList);
	}

	public int getCode() {
		return this.code;
	}

	public List<ErrorDto> getErrorList() {
		return errorList;
	}
}
