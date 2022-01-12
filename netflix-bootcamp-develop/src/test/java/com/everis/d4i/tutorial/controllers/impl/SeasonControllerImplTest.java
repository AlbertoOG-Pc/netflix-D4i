package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.services.impl.SeasonServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
class SeasonControllerImplTest {
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	@Mock
	private SeasonRepository seasonRepository;

	@Mock
	private SeasonServiceImpl seasonService;

	@InjectMocks
	private SeasonControllerImpl seasonController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(seasonController).build();
	}

	@Test
	void testGetSeasonsByTvShow() throws Exception {
		SeasonRest seasonRest1 = new SeasonRest();
		seasonRest1.setId(1L);
		seasonRest1.setName("Season 1");
		seasonRest1.setNumber((short)1 );
		
		SeasonRest seasonRest2 = new SeasonRest();
		seasonRest2.setId(2L);
		seasonRest2.setName("Season 2");
		seasonRest2.setNumber((short)2 );
		
		List<SeasonRest> listSeason = new ArrayList<>(Arrays.asList(seasonRest1,seasonRest2));
		
		Mockito.when(seasonService.getSeasonsByTvShow(1L)).thenReturn(listSeason);
		 
		mockMvc.perform(get(
				RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_SEASON, 1))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.length()", is(2))).andExpect(jsonPath("$.data[0].name", is("Season 1")))
				.andExpect(jsonPath("$.data[1].id", is(2)));
	}

	@Test
	void testGetSeasonByTvShowIdAndSeasonNumber() throws Exception {
		SeasonRest seasonRest1 = new SeasonRest();
		seasonRest1.setId(1L);
		seasonRest1.setName("Season 1");
		seasonRest1.setNumber((short)1 );
		
		
		Mockito.when(seasonService.getSeasonByTvShowIdAndSeasonNumber(1L, (short)1 )).thenReturn(seasonRest1);
		 
		mockMvc.perform(get(
				RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_SEASON + RestConstants.RESOURCE_NUMBER, 1, 1))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.name", is("Season 1")))
				.andExpect(jsonPath("$.data.id", is(1)));
	}

}
