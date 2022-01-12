package com.everis.d4i.tutorial.controllers;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface TvShowController {

	NetflixResponse<List<TvShowRest>> getTvShowsByCategory(Long categoryId) throws NetflixException;

	NetflixResponse<TvShowRest> getTvShowById(Long id) throws NetflixException;
	
	NetflixResponse<TvShowRest> createTvShow(TvShowRest tvShowRest) throws NetflixException;
	
	NetflixResponse<TvShowRest> updateTvShowById(TvShowRest tvShowRest, Long id) throws NetflixException;

	NetflixResponse<TvShowRest> deleteTvShowById(Long id) throws NetflixException; 

}
