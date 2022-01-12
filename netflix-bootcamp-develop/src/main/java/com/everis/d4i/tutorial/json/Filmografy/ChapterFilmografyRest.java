package com.everis.d4i.tutorial.json.Filmografy;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterFilmografyRest implements Serializable {

	private static final long serialVersionUID = 7761280193290803367L;

	private short numberChapter;
	private String nameChapter;

	public short getNumberChapter() {
		return numberChapter;
	}

	public void setNumberChapter(short numberChapter) {
		this.numberChapter = numberChapter;
	}

	public String getNameChapter() {
		return nameChapter;
	}

	public void setNameChapter(String nameChapter) {
		this.nameChapter = nameChapter;
	}

}
