package com.everis.d4i.tutorial.json.Filmografy;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TvShowFilmografyRest implements Serializable {

	private static final long serialVersionUID = 6160011390150047726L;
	private String nameTvShow;
	private String shortDescription;
	private byte recommendedAge;
	private List<SeasonFilmografyRest> seasons;

	public String getNameTvShow() {
		return nameTvShow;
	}

	public void setNameTvShow(String nameTvShow) {
		this.nameTvShow = nameTvShow;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public byte getRecommendedAge() {
		return recommendedAge;
	}

	public void setRecommendedAge(byte recommendedAge) {
		this.recommendedAge = recommendedAge;
	}

	public List<SeasonFilmografyRest> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<SeasonFilmografyRest> seasons) {
		this.seasons = seasons;
	}

}
