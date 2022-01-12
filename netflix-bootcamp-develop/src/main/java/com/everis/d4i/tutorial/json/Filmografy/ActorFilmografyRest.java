package com.everis.d4i.tutorial.json.Filmografy;

import java.io.Serializable;
import java.util.List;

import com.everis.d4i.tutorial.json.ActorRest;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorFilmografyRest implements Serializable {

	private static final long serialVersionUID = -1501113160652971522L;

	private List<TvShowFilmografyRest> shows;
	private ActorRest actor;

	public List<TvShowFilmografyRest> getShows() {
		return shows;
	}

	public void setShows(List<TvShowFilmografyRest> shows) {
		this.shows = shows;
	}

	public ActorRest getActor() {
		return actor;
	}

	public void setActor(ActorRest actor) {
		this.actor = actor;
	}

}
