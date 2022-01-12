package com.everis.d4i.tutorial.services;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;

public interface ActorService {
	List<ActorRest> getActors() throws NetflixException;
	ActorRest getActorById(Long id) throws NetflixException;
	ActorRest createActor(@Valid ActorRest actorRest) throws NetflixException;
	ActorRest updateActorName(@Valid ActorRest actorRest, Long id) throws NetflixException;
	void deleteActorById(Long id) throws NetflixException;
	ActorFilmografyRest getActorFilmography(Long id) throws NetflixException;
}
