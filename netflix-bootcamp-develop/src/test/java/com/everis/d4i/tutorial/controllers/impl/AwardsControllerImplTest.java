package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

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

import com.everis.d4i.tutorial.json.AwardsRest;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.AwardsRepository;
import com.everis.d4i.tutorial.services.impl.AwardServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
class AwardsControllerImplTest {
	
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	@Mock
	private AwardsRepository awardsRepository;

	@Mock
	private AwardServiceImpl awardService;
	
	@InjectMocks
	private AwardsControllerImpl awardController;


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(awardController).build();
		//this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}
	
	@DisplayName("Test for Awards by TV SHOW")
	@Test
	void testGetAwardsByTvShowId() throws Exception {
		TvShowRest tvshow = new TvShowRest();
		tvshow.setId(1L);
		tvshow.setName("Juego de tronos");
		
		AwardsRest award1 = new AwardsRest();
		award1.setId(1L);
		award1.setName("Mejor drama");
		
		TvShowAwardRest tvShowAwardRest1 = new TvShowAwardRest();
		tvShowAwardRest1.setAward(award1);
		tvShowAwardRest1.setTvShow(tvshow);
		tvShowAwardRest1.setYear(Year.of(2016));
		
		List<TvShowAwardRest> listAwardsRest = new ArrayList<>();
		listAwardsRest.add(tvShowAwardRest1);
		
		Mockito.when(awardService.getAwardsByTvShowId(1L)).thenReturn(listAwardsRest);
		
		mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_AWARDS, 1L))
				//Validar respuesta
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.length()", is(1)))
				.andExpect(jsonPath("$.data[0].award.name", is("Mejor drama")))
				.andExpect(jsonPath("$.data[0].tvShow.name", is("Juego de tronos")));
	}

}
