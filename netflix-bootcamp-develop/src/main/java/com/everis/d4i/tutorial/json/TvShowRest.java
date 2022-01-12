package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Tv Show")
public class TvShowRest implements Serializable {

	private static final long serialVersionUID = 4916713904971425156L;

	//TODO: cambiar a accesMode, readonly deprecated
	@ApiModelProperty(readOnly = true)
	private Long id;
	
	private String name;
	private String shortDescription;
	private String longDescription;
	private Year year;
	private byte recommendedAge;
	private List<CategoryRest> categories;
	private String advertising;

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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public byte getRecommendedAge() {
		return recommendedAge;
	}

	public void setRecommendedAge(byte recommendedAge) {
		this.recommendedAge = recommendedAge;
	}

	public List<CategoryRest> getCategories() {
		return categories;
	}

	public void setCategories(List <CategoryRest> category) {
		this.categories = category;
	}

	public String getAdvertising() {
		return advertising;
	}

	public void setAdvertising(String advertising) {
		this.advertising = advertising;
	}

}
