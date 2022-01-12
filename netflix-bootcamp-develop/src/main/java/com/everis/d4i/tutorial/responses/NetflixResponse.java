package com.everis.d4i.tutorial.responses;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Response")
public class NetflixResponse<T> implements Serializable {

	@ApiModelProperty(position = 0)
	private String status;
	@ApiModelProperty(position = 1)
	private String code;
	@ApiModelProperty(position = 2)
	private String message;
	@ApiModelProperty(position = 3)
	private T data;

	private static final long serialVersionUID = 7302319210373510173L;

	public NetflixResponse() {
		super();
	}

	public NetflixResponse(String status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public NetflixResponse(String status, String code, String message, T data) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
