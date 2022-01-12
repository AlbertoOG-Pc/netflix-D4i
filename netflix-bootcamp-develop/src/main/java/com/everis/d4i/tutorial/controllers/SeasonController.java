package com.everis.d4i.tutorial.controllers;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface SeasonController {

	NetflixResponse<List<SeasonRest>> getSeasonsByTvShow(Long tvShowId) throws NetflixException;

	NetflixResponse<SeasonRest> getSeasonByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber)
			throws NetflixException;

}
