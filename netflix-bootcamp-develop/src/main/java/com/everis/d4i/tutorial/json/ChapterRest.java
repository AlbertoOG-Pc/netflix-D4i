package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Chapter")
public class ChapterRest implements Serializable {

	private static final long serialVersionUID = 8725949484031409482L;

	//TODO: cambiar a accesMode, readonly deprecated
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	@ApiModelProperty(readOnly = true)
	private short number;
	private String name;
	@ApiModelProperty(readOnly = true)
	private short duration;
	@ApiModelProperty(readOnly = true)
	private List <ActorRest> actors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getNumber() {
		return number;
	}

	public void setNumber(short number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getDuration() {
		return duration;
	}

	public void setDuration(short duration) {
		this.duration = duration;
	}

	public List<ActorRest> getActors() {
		return actors;
	}

	public void setActors(List<ActorRest> actors) {
		this.actors = actors;
	}
	
	

}
