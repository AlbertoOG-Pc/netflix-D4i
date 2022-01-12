package com.everis.d4i.tutorial.controllers;

import java.util.List;

import javax.validation.Valid;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface ActorController {
	
	NetflixResponse<List<ActorRest>> getActors() throws NetflixException;
	
	NetflixResponse<ActorRest> getActorById(Long id) throws NetflixException;
	
	NetflixResponse<ActorRest> createActor(ActorRest actorRest) throws NetflixException;

	NetflixResponse<ActorRest> updateActorNameById(@Valid ActorRest actorRest, Long id) throws NetflixException;

	NetflixResponse<ActorRest> deleteActorById(Long id) throws NetflixException;

	NetflixResponse<ActorFilmografyRest> getActorFilmography(Long id) throws NetflixException;	
}
