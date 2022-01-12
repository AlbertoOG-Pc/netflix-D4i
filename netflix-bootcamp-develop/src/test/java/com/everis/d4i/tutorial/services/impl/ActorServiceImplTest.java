package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.ChapterFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.SeasonFilmografyRest;
import com.everis.d4i.tutorial.json.Filmografy.TvShowFilmografyRest;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class ActorServiceImplTest {

	@Autowired
	//private MockMvc mockMvc;
	
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private ActorRepository actorRepository;
	
	@Mock
	private TvShowRepository tvShowRepository;

	@Mock
	private SeasonRepository seasonRepository;

	@Mock
	private ChapterRepository chapterRepository;

	@InjectMocks
	private ActorServiceImpl actorService;

	Actor actor1 = new Actor(1L, "Alberto Otero");
	Actor actor2 = new Actor(2L, "Jose Ramirez");
	Actor actor3 = new Actor(3L, "Carlos Mendez");
	Actor actor4 = new Actor(4L, "Alberto Otero Modificado");

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		//this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}

	@DisplayName("SERVICE - Get all actors")
	@Test
	void testGetActors() throws NetflixException {
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);

		Mockito.when(actorRepository.findAll()).thenReturn(actors);
		assertThat(actors).isNotNull().isNotEmpty().hasSize(1);
	}

	@DisplayName("SERVICE - Get actors by ID")
	@Test
	void testGetActorById() throws NetflixException {
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);
		actors.add(actor2);
		actors.add(actor3);
		Optional<Actor> actorOptional1 = Optional.of(actor1);
		Optional<Actor> actorOptional2 = Optional.empty();

		Mockito.when(actorRepository.findById(1L)).thenReturn(actorOptional1);
		Mockito.when(actorRepository.findById(100L)).thenReturn(actorOptional2);
		// Mockito.doThrow(new NetflixException()).when(actorRepository).getOne(5L);
		// Mockito.when(actorRepository.getOne(5L)).thenThrow(NetflixException.class);

		ActorRest actorTest = actorService.getActorById(1L);

		assertThat(actorTest).isNotNull();
		assertThat(actorTest.getId()).isEqualTo(1L);
		assertThat(actorTest.getName()).isEqualTo("Alberto Otero");
		
		Exception exception = assertThrows(NotFoundException.class, () -> {
			actorService.getActorById(100L);
	    });

	    String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	    
		//ActorRest actorError = actorService.getActorById(100L);
		// Mockito.when(actorService.getActorById(5L)).thenThrow(NotFoundException.class);
	}

	@DisplayName("SERVICE - Create Actor")
	@Test
	void testCreateActor() throws NetflixException {
		List<Actor> actors = new ArrayList<>();
		actors.add(actor1);

		ActorRest actorSave = new ActorRest();
		actorSave.setName("Alberto Otero");
		
		//Actor actorError = new Actor(10L,"");

		Mockito.when(actorRepository.save(Mockito.any(Actor.class))).thenReturn(actor1);
		
		//Mando un por sencillez pero deberia ser MethodArgumentNotValidException que es lo que devuelve el @valid
		Mockito.when(actorRepository.save(new Actor(anyLong(), ""))).thenThrow(new NullPointerException("aa"));  
		Mockito.when(actorRepository.save(new Actor(anyLong(), null))).thenThrow(new NullPointerException("aa")); 
		Mockito.when(actorRepository.findAll()).thenReturn(actors);												  
																												 
		ActorRest actorTestSave = actorService.createActor(actorSave);
		ActorRest actorTestError = new ActorRest();
		actorTestError.setId(10L);
		actorTestError.setName("");

		assertThat(actorTestSave).isNotNull();
		assertThat(actorTestSave.getId()).isEqualTo(1L);
		assertThat(actorTestSave.getName()).isEqualTo("Alberto Otero");
		assertThat(actorService.getActors()).size().isEqualTo(1);
		
		Exception exception = assertThrows(InternalServerErrorException.class, () -> {
			actorService.createActor(actorTestError);
	    });

	    String expectedMessage = ExceptionConstants.INTERNAL_SERVER_ERROR;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@DisplayName("SERVICE - Update Actor")
	@Test
	void testUpdateActorName() throws NetflixException {
		Actor actorInitial = new Actor(6L, "Actor Alberto Otero modificado");
		
		ActorRest actorUpdate = new ActorRest();
		actorUpdate.setId(6L);
		actorUpdate.setName("Actor Alberto Otero modificado");

		Mockito.when(actorRepository.getOne(6L)).thenReturn(actor1);
		Mockito.when(actorRepository.save(Mockito.any(Actor.class))).thenReturn(actorInitial);
		
		ActorRest finalActor = actorService.updateActorName(actorUpdate, 6L);

		assertThat(finalActor).isEqualTo(actorUpdate).isNotNull();
	}

	@DisplayName("SERVICE - Delete Actor")
	@Test
	void testDeleteActorById() throws NetflixException {
		doThrow(new IllegalArgumentException()).when(actorRepository).deleteById(100L);
		
		Exception exception = assertThrows(NotFoundException.class, () -> {
			actorService.deleteActorById(100L);
	    });

	    String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

	@DisplayName("SERVICE - Actor Filmografy")
	@Test
	void testGetActorFilmography() throws NetflixException {
		Actor actorJuego = new Actor(1L, "Sophie Turner");
		
		TvShow tvShow = new TvShow();
		tvShow.setId(1L);
		tvShow.setName("Juego de tronos");
		
		Season season1 = new Season();
		season1.setId(1L);
		season1.setName("Juego de tronos - One");
		season1.setNumber((short)1);	
		season1.setTvShow(tvShow);
		
		Chapter chapter1 = new Chapter();
		chapter1.setId(1L);
		chapter1.setDuration((short) 60);
		chapter1.setName("Chapter 1");
		chapter1.setSeason(season1);
		
		List<Season> seasons = new ArrayList<>();
		seasons.add(season1);
		tvShow.setSeasons(seasons);
		
		List<Chapter> chapters = new ArrayList<>();
		chapters.add(chapter1);
		season1.setChapters(chapters);
		Optional<Actor> optionalActor = Optional.of(actorJuego);
		
		Mockito.when(actorRepository.findById(1L)).thenReturn(optionalActor);
		Mockito.when(chapterRepository.findByActorsId(1L)).thenReturn(chapters);
		Mockito.when(seasonRepository.getOne(1L)).thenReturn(season1);
		Mockito.when(tvShowRepository.getOne(1L)).thenReturn(tvShow);
		
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
		
		actorFilmografyRest = actorService.getActorFilmography(1L);
		
		assertThat(actorFilmografyRest.getActor()).isEqualTo(actorSophie);
		assertThat(actorFilmografyRest.getShows().size()).isEqualTo(1);
		assertThat(actorFilmografyRest.getShows().get(0).getNameTvShow()).isEqualTo(tvShow.getName());
		assertThat(actorFilmografyRest.getShows().get(0).getSeasons().size()).isEqualTo(1);
		assertThat(actorFilmografyRest.getShows().get(0).getSeasons().get(0).getNameSeason()).isEqualTo(season1.getName());
		assertThat(actorFilmografyRest.getShows().get(0).getSeasons().get(0).getChapters().size()).isEqualTo(1);
		assertThat(actorFilmografyRest.getShows().get(0).getSeasons().get(0).getChapters().get(0).getNameChapter()).isEqualTo(chapter1.getName());
	}

}
