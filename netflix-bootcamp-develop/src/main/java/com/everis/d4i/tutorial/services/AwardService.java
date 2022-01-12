package com.everis.d4i.tutorial.services;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;

public interface AwardService {
	List<TvShowAwardRest> getAwardsByTvShowId(Long tvShowId) throws NetflixException;
}
