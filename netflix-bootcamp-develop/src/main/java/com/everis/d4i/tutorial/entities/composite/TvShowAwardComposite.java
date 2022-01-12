package com.everis.d4i.tutorial.entities.composite;

import java.io.Serializable;
import java.time.Year;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TvShowAwardComposite implements Serializable {

	private static final long serialVersionUID = -6911534380252132131L;

	@Column(name = "TV_SHOW_ID")
	private Integer tvShow;
	
	@Column(name = "AWARD_ID")
	private Integer award;

	@Column(name = "YEAR")
	private Year year;

	public Integer getTvShow() {
		return tvShow;
	}

	public void setTvShow(Integer tvShow) {
		this.tvShow = tvShow;
	}

	public Integer getAward() {
		return award;
	}

	public void setAward(Integer award) {
		this.award = award;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}
}
