package com.everis.d4i.tutorial.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.services.ChapterService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class ChapterServiceImpl implements ChapterService {

	@Autowired
	private ChapterRepository chapterRepository;

	private ModelMapper modelMapper = new ModelMapper();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChapterServiceImpl.class);

	@Override
	public List<ChapterRest> getChaptersByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber)
			throws NetflixException {
		Optional<List<ChapterRest>> listChapterRest = Optional.of(chapterRepository
				.findBySeasonTvShowIdAndSeasonNumber(tvShowId, seasonNumber)
				.orElseThrow(()-> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW_OR_SEASON))
				.stream()
				.map(chapter -> modelMapper.map(chapter, ChapterRest.class))
				.collect(Collectors.toList()));
		
		return listChapterRest.get();
	}

	@Override
	public ChapterRest getChapterByTvShowIdAndSeasonNumberAndChapterNumber(Long tvShowId, short seasonNumber,
			short chapterNumber) throws NetflixException {
		Chapter chapter = chapterRepository
				.findBySeasonTvShowIdAndSeasonNumberAndNumber(tvShowId, seasonNumber, chapterNumber)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER));
		return modelMapper.map(chapter, ChapterRest.class);
	}

	@Override
	public ChapterRest updateChapters(Long tvShowId, short seasonNumber, 
			ChapterRest chapterRest, short chapterNumber) throws NetflixException {
		ChapterRest chapterRegistered = 
				getChapterByTvShowIdAndSeasonNumberAndChapterNumber(
						tvShowId, seasonNumber, chapterNumber);
				
		Chapter chapterToSave = chapterRepository.getOne(chapterRegistered.getId());
		chapterToSave.setName(chapterRest.getName());
		try {
			chapterToSave = chapterRepository.save(chapterToSave);
		}catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		
		return modelMapper.map(chapterToSave, ChapterRest.class);
	}

}
