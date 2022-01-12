package com.everis.d4i.tutorial.services;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.SeasonRest;

public interface SeasonService {

	List<SeasonRest> getSeasonsByTvShow(Long tvShowId) throws NetflixException;

	SeasonRest getSeasonByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber) throws NetflixException;

}
