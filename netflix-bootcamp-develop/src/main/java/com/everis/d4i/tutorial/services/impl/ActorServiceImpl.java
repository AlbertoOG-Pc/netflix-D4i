package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.TvShowFilmografyRest;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
@Validated
public class ActorServiceImpl implements ActorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActorServiceImpl.class);

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	TvShowRepository tvShowRepository;

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	ChapterRepository chapterRepository;

	private ModelMapper modelMapper = new ModelMapper();
	private Set<ConstraintViolation<ActorRest>> violations;

	@Override
	public List<ActorRest> getActors() throws NetflixException {
		return actorRepository.findAll().stream().map(actor -> modelMapper.map(actor, ActorRest.class))
				.collect(Collectors.toList());
	}

	@Override
	public ActorRest getActorById(Long id) throws NetflixException {
		Actor actor = actorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));
		return modelMapper.map(actor, ActorRest.class);
	}

	@Validated(Default.class)
	@Override
	public ActorRest createActor(@Valid ActorRest actorRest) throws NetflixException {
		Actor actor = new Actor();
		try {
			violations = validator.validate(actorRest);
			if (!violations.isEmpty()) {
				throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
			}
			actor.setId(actorRest.getId());
			actor.setName(actorRest.getName());
			actor = actorRepository.save(actor);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}

		return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public ActorRest updateActorName(@Valid ActorRest actorRest, Long id) throws NetflixException {
		Actor actor = new Actor();
		try {
			violations = validator.validate(actorRest);
			if (!violations.isEmpty()) {
				throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
			}
			actor = actorRepository.getOne(id);
			actor.setName(actorRest.getName());
			actor = actorRepository.save(actor);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}

		return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public void deleteActorById(Long id) throws NetflixException {
		getActorById(id);
		try {
			actorRepository.deleteById(id);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ActorFilmografyRest getActorFilmography(Long id) throws NetflixException {
		ActorFilmografyRest actorFilmografyRest = new ActorFilmografyRest();
		actorFilmografyRest.setActor(modelMapper.map(getActorById(id), ActorRest.class));

		// Lista de capitulos del actor
		List<Chapter> chapterList = chapterRepository.findByActorsId(id);

		// Listado de ids de las seasons
		List<Long> sesionsIds = chapterList.stream().map(chapter -> chapter.getSeason().getId()).distinct()
				.collect(Collectors.toList());

		// Obtenemos el listado de seasons apartir de la lista de ids anterior
		final List<Season> seasonsList = new ArrayList<>();
		sesionsIds.stream().forEach(seasonId -> seasonsList.add(seasonRepository.getOne(seasonId)));

		// Lista de ids tvShows
		List<Long> tvShowIds = seasonsList.stream().map(season -> season.getTvShow().getId()).distinct()
				.collect(Collectors.toList());
		tvShowIds.stream().forEach(x -> System.out.println(x));

		// Obtenemos el listado de tvshow apartir de la lista de ids anterior
		final List<TvShow> tvShowList = new ArrayList<>();
		tvShowIds.stream().forEach(tvShowId -> tvShowList.add(tvShowRepository.getOne(tvShowId)));

		actorFilmografyRest.setShows(tvShowList.stream()
				.map(tvShow -> modelMapper.map(tvShow, TvShowFilmografyRest.class)).collect(Collectors.toList()));

		return actorFilmografyRest;
	}

}
