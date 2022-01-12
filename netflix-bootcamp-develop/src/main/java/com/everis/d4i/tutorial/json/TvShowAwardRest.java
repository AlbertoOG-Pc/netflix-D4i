package com.everis.d4i.tutorial.json;

import java.io.Serializable;
import java.time.Year;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "TvShowAward")
public class TvShowAwardRest implements Serializable {

	private static final long serialVersionUID = 4860574199418708002L;

	@ApiModelProperty(position = 2)
	private AwardsRest award;
	@ApiModelProperty(position = 0)
	private TvShowRest tvShow;
	@ApiModelProperty(position = 1)
	private Year year;


	public AwardsRest getAward() {
		return award;
	}

	public void setAward(AwardsRest award) {
		this.award = award;
	}

	public TvShowRest getTvShow() {
		return tvShow;
	}

	public void setTvShow(TvShowRest tvShow) {
		this.tvShow = tvShow;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}
