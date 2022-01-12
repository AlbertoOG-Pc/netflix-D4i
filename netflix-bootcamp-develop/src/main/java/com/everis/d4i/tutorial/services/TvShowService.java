package com.everis.d4i.tutorial.services;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowRest;

public interface TvShowService {

	List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException;

	TvShowRest getTvShowById(Long id) throws NetflixException;
	
	TvShowRest createTvShow(TvShowRest tvShowRest) throws NetflixException;
	
	TvShowRest updateTvShow(TvShowRest tvShowRest, Long id) throws NetflixException;

	void deleteTvShowById(Long id) throws NetflixException;

}
