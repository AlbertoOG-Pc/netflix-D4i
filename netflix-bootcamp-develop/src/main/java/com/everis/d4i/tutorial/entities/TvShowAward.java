package com.everis.d4i.tutorial.entities;

import java.io.Serializable;
import java.time.Year;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.everis.d4i.tutorial.entities.composite.TvShowAwardComposite;

@Entity
@Table(name = "TV_SHOWS_AWARDS")
public class TvShowAward implements Serializable {

	private static final long serialVersionUID = 5302126980239255060L;

	@EmbeddedId
	private TvShowAwardComposite primaryKey;
	
	@ManyToOne
	@MapsId("award")
	private Awards award;
	
	@ManyToOne
	@MapsId("tvShow")
	private TvShow tvShow;
			

	public Awards getAward() {
		return award;
	}

	public void setAward(Awards award) {
		this.award = award;
	}

	public TvShow getTvShow() {
		return tvShow;
	}

	public void setTvShow(TvShow tvShow) {
		this.tvShow = tvShow;
	}

	public TvShowAwardComposite getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(TvShowAwardComposite primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Year getYear() {
		return primaryKey.getYear();
	}

	
	

}
