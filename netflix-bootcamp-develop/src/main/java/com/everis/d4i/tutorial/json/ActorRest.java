package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Actor")
public class ActorRest implements Serializable {

	private static final long serialVersionUID = 2562292635410148858L;

	//TODO: cambiar a accesMode, readonly deprecated
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	@NotBlank(message = "Nombre en blanco")
	@ApiModelProperty(example = "Actor name")
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActorRest other = (ActorRest) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
}
