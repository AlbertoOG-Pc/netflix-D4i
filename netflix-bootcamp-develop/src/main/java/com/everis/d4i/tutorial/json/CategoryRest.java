package com.everis.d4i.tutorial.json;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Category")
public class CategoryRest implements Serializable {

	private static final long serialVersionUID = 180802329613616000L;

	//TODO: cambiar a accesMode, readonly deprecated
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	@NotBlank(message = "Nombre en blanco")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
