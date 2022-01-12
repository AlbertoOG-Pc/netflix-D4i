package com.everis.d4i.tutorial.controllers;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface ChapterController {

	NetflixResponse<List<ChapterRest>> getChaptersByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber)
			throws NetflixException;

	NetflixResponse<ChapterRest> getChapterByTvShowIdAndSeasonNumberAndChapterNumber(Long tvShowId, short seasonNumber,
			short chapterNumber) throws NetflixException;

	NetflixResponse<ChapterRest> updateByTvShowIdAndSeasonNumberAndChapterNumber(Long tvShowId, short seasonNumber,
			ChapterRest chapterRest, short chapterNumber) throws NetflixException;

}
