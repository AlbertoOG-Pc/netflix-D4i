package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class TvShowServiceImpl implements TvShowService {

	@Autowired
	private TvShowRepository tvShowRepository;

	private ModelMapper modelMapper = new ModelMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(TvShowServiceImpl.class);

	@Override
	public List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException {
		Optional<List<TvShowRest>> listTvShowRest = Optional.of(tvShowRepository
				.findByCategoriesId(categoryId)
				.orElseThrow(()-> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW))
				.stream()
				.map(tvShow -> modelMapper.map(tvShow, TvShowRest.class))
				.collect(Collectors.toList()));
		
		return listTvShowRest.get();
	}

	@Override
	public TvShowRest getTvShowById(Long id) throws NetflixException {
		TvShow tvShow = tvShowRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW));
		return modelMapper.map(tvShow, TvShowRest.class);
	}

	@Override
	public TvShowRest createTvShow(TvShowRest tvShowRest) throws NetflixException {
		TvShow tvShow = new TvShow();

		List<Category> listEntitiesCategories = new ArrayList<>();
		// Conversion de CategoryRest a Category
		tvShowRest.getCategories().stream()
				.forEach(x -> listEntitiesCategories.add(new Category(x.getId(), x.getName())));

		tvShow.setAdvertising(tvShowRest.getAdvertising());
		tvShow.setCategories(listEntitiesCategories);
		tvShow.setLongDescription(tvShowRest.getLongDescription());
		tvShow.setName(tvShowRest.getName());
		tvShow.setRecommendedAge(tvShowRest.getRecommendedAge());
		tvShow.setShortDescription(tvShowRest.getShortDescription());
		tvShow.setYear(tvShowRest.getYear());

		try {
			tvShow = tvShowRepository.save(tvShow);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}

		return modelMapper.map(tvShow, TvShowRest.class);
	}

	@Override
	public TvShowRest updateTvShow(TvShowRest tvShowRest, Long id) throws NetflixException {
		TvShow tvShow = new TvShow();
		try {
			tvShow = tvShowRepository.getOne(id);
			tvShow.setName(tvShowRest.getName());
			tvShowRepository.save(tvShow);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}

		return modelMapper.map(tvShow, TvShowRest.class);
	}

	@Override
	public void deleteTvShowById(Long id) throws NetflixException {
		try {
			tvShowRepository.deleteById(id);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
	}

}
