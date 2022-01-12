package com.everis.d4i.tutorial.services;

import java.util.List;


import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ChapterRest;

public interface ChapterService {

	List<ChapterRest> getChaptersByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber) throws NetflixException;

	ChapterRest getChapterByTvShowIdAndSeasonNumberAndChapterNumber(Long tvShowId, short seasonNumber,
			short chapterNumber) throws NetflixException;

	ChapterRest updateChapters(Long tvShowId, short seasonNumber, ChapterRest chapterRest, short id) throws NetflixException;

}
