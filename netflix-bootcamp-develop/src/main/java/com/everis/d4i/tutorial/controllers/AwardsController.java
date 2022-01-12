package com.everis.d4i.tutorial.controllers;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface AwardsController {

	NetflixResponse<List<TvShowAwardRest>> getAwardsByTvShowId(Long tvShowId) throws NetflixException;
}
