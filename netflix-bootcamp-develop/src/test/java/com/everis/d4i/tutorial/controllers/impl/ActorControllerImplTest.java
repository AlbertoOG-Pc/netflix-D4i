package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.ChapterFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.SeasonFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.TvShowFilmografyRest;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.services.impl.ActorServiceImpl;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
class ActorControllerImplTest {

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private ActorRepository actorRepository;

	@Mock
	private ActorServiceImpl actorService;
	
	@InjectMocks
	private ActorControllerImpl actorController;

	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(actorController).build();
		//this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}

	@DisplayName("Test for get all Actors")
	@Test
	void testGetActors() throws Exception {
		ActorRest actorRest1 = new ActorRest();
		actorRest1.setId(1L);
		actorRest1.setName("Alberto");
		List<ActorRest> actorsRest = new ArrayList<>(Arrays.asList(actorRest1));
				
		Mockito.when(actorService.getActors()).thenReturn(actorsRest);
		
		mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR))
				//Validar respuesta
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//Validar campos
				.andExpect(jsonPath("$.data[0].name", is("Alberto")))
				.andExpect(jsonPath("$.data.length()", is(1)));
	}

	@Test
	void testGetActorById() throws Exception, NetflixException {
		ActorRest actorRest1 = new ActorRest();
		actorRest1.setId(1L);
		actorRest1.setName("Alberto");
		//Optional<Actor> actorOptional = Optional.empty();
				
		Mockito.when(actorService.getActorById(1L)).thenReturn(actorRest1);
		Mockito.when(actorService.getActorById(100L)).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));
		//Mockito.when(actorRepository.findById(100L)).thenReturn(actorOptional);
		
		mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + "/1"))
				//Validar respuesta
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//Validar campos
				.andExpect(jsonPath("$.data.name", is("Alberto")))
				.andExpect(jsonPath("$.data.id", is(1)));
		
		/*mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + "/100"))
				//Validar respuesta
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				//Validar campos
				.andExpect(jsonPath("$.message", is(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR)))
				.andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("$.data", Matchers.emptyArray()));*/
	}

	@Test
	void testCreateActor() throws Exception, NetflixException {
		ActorRest actorRest1 = new ActorRest();
		actorRest1.setId(1L);
		actorRest1.setName("Alberto");
		
		Mockito.when(actorService.createActor(Mockito.any(ActorRest.class))).thenReturn(actorRest1);
		
		mockMvc.perform(
				post(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Alberto para Mockito\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id", is(1)))
				.andExpect(jsonPath("$.data.name", is("Alberto")));
	}

	@Test
	void testUpdateActorNameById() throws Exception {
		ActorRest actorRest1 = new ActorRest();
		actorRest1.setId(1L);
		actorRest1.setName("Alberto");
		
		ActorRest actorRest2 = new ActorRest();
		actorRest2.setId(1L);
		actorRest2.setName("Alberto Modificado");
		
		Mockito.when(actorService.updateActorName(Mockito.any(ActorRest.class), Mockito.any(Long.class))).thenReturn(actorRest2);
				
		mockMvc.perform(
				patch(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + "/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Alberto Modificado\"}"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id", is(1)))
				.andExpect(jsonPath("$.data.name", is("Alberto Modificado"))
				);
	}

	@Test
	void testDeleteActorById() throws Exception {
		mockMvc.perform(
				delete(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + "/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	void testGetActorFilmography() throws Exception {
		ActorFilmografyRest actorFilmografyRest = new ActorFilmografyRest();
		
		ActorRest actorSophie = new ActorRest();
		actorSophie.setId(1L);
		actorSophie.setName("Sophie Turner");
		
		TvShowFilmografyRest tvShowFilmografy = new TvShowFilmografyRest();
		tvShowFilmografy.setNameTvShow("Juego de tronos");
		
		SeasonFilmografyRest seasonFilmografy = new SeasonFilmografyRest();
		seasonFilmografy.setNameSeason("Juego de tronos - One");
		seasonFilmografy.setNumberSeason((short)1);
		
		List<SeasonFilmografyRest> seasonsFilmografy = new ArrayList<>();
		seasonsFilmografy.add(seasonFilmografy);
		tvShowFilmografy.setSeasons(seasonsFilmografy);
		
		ChapterFilmografyRest chapterFilmografy = new ChapterFilmografyRest();
		chapterFilmografy.setNameChapter("Chapter 1");
		
		List<ChapterFilmografyRest> chaptersFilmografy = new ArrayList<>();
		chaptersFilmografy.add(chapterFilmografy);
		seasonFilmografy.setChapters(chaptersFilmografy);
		
		actorFilmografyRest.setActor(actorSophie);
		actorFilmografyRest.setShows(Arrays.asList(tvShowFilmografy));
		
		Mockito.when(actorService.getActorFilmography(1L)).thenReturn(actorFilmografyRest);
		
//		String result = mockMvc.perform(
//				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + RestConstants.RESOURCE_FILMOGRAPHY + "/1")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString();
//		
		mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR + RestConstants.RESOURCE_FILMOGRAPHY + "/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.shows.length()", is(1)))
				.andExpect(jsonPath("$.data.shows[0].seasons.length()", is(1)))
				.andExpect(jsonPath("$.data.shows[0].seasons[0].chapters.length()", is(1)));
	}

}
