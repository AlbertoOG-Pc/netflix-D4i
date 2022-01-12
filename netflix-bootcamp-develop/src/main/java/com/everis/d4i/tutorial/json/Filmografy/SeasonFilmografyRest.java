package com.everis.d4i.tutorial.json.Filmografy;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeasonFilmografyRest implements Serializable {

	private static final long serialVersionUID = -1398928378986430376L;

	private String nameSeason;
	private short numberSeason;
	private List<ChapterFilmografyRest> chapters;

	public String getNameSeason() {
		return nameSeason;
	}

	public void setNameSeason(String nameSeason) {
		this.nameSeason = nameSeason;
	}

	public short getNumberSeason() {
		return numberSeason;
	}

	public void setNumberSeason(short numberSeason) {
		this.numberSeason = numberSeason;
	}

	public List<ChapterFilmografyRest> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterFilmografyRest> chapters) {
		this.chapters = chapters;
	}
	
	

}
