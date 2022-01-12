package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Year;
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
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.impl.TvShowServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { SecurityConfig.class })
class TvShowControllerImplTest {
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private TvShowRepository tvShowRepository;

	@Mock
	private TvShowServiceImpl tvShowService;

	@InjectMocks
	private TvShowControllerImpl tvShowController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(tvShowController).build();
	}

	@Test
	void testGetTvShowsByCategory() throws Exception {
		TvShowRest tvShow1 = new TvShowRest();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos");
		TvShowRest tvShow2 = new TvShowRest();
		tvShow2.setId(2L);
		tvShow2.setName("Los 100");

		List<TvShowRest> listTvShow = new ArrayList<>(Arrays.asList(tvShow1, tvShow2));

		Mockito.when(tvShowService.getTvShowsByCategory(1L)).thenReturn(listTvShow);

		mockMvc.perform(get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1
				+ RestConstants.RESOURCE_TV_SHOW + "?categoryId=1"))

				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.length()", is(2)))
				.andExpect(jsonPath("$.data[0].name", is("Juego de tronos")))
				.andExpect(jsonPath("$.data[1].id", is(2)));

	}

	@Test
	void testGetTvShowById() throws Exception {
		TvShowRest tvShow1 = new TvShowRest();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos");

		Mockito.when(tvShowService.getTvShowById(1L)).thenReturn(tvShow1);

		mockMvc.perform(get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1
				+ RestConstants.RESOURCE_TV_SHOW + RestConstants.RESOURCE_ID, 1))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.name", is("Juego de tronos"))).andExpect(jsonPath("$.data.id", is(1)));
	}

	@Test
	void testCreateTvShow() throws Exception {
		CategoryRest category1 = new CategoryRest();
		category1.setId(1L);
		category1.setName("ACCION");
		List<CategoryRest> listEntitiesCategories = new ArrayList<>(Arrays.asList(category1));
		TvShowRest tvShow1 = new TvShowRest();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos");
		tvShow1.setAdvertising("Muertes");
		tvShow1.setCategories(listEntitiesCategories);
		tvShow1.setLongDescription("Description long");
		tvShow1.setRecommendedAge(Byte.parseByte("18"));
		tvShow1.setShortDescription("Description short");
		tvShow1.setYear(Year.of(2012));

		Mockito.when(tvShowService.createTvShow(Mockito.any(TvShowRest.class))).thenReturn(tvShow1);

		mockMvc.perform(
				post(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_TV_SHOW)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"advertising\": \"Muertes\",\"categories\": [{\"id\": 1,\"name\": \"ACCION\"}],"
								+ "\"longDescription\": \"Description long\",\"name\": \"Juego de tronos\",\"recommendedAge\": 18,"
								+ "\"shortDescription\": \"Description short\",\"year\": 2012}"))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id", is(1))).andExpect(jsonPath("$.data.advertising", is("Muertes")))
				.andExpect(jsonPath("$.data.categories[0].name", is("ACCION")))
				.andExpect(jsonPath("$.data.recommendedAge", is(18))).andExpect(jsonPath("$.data.year", is(2012)));

	}

	@Test
	void testUpdateTvShowById() throws Exception {
		CategoryRest category1 = new CategoryRest();
		category1.setId(1L);
		category1.setName("ACCION");
		List<CategoryRest> listEntitiesCategories = new ArrayList<>(Arrays.asList(category1));
		TvShowRest tvShow1 = new TvShowRest();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos UPDATED");
		tvShow1.setAdvertising("Muertes");
		tvShow1.setCategories(listEntitiesCategories);
		tvShow1.setLongDescription("Description long");
		tvShow1.setRecommendedAge(Byte.parseByte("18"));
		tvShow1.setShortDescription("Description short");
		tvShow1.setYear(Year.of(2012));

		TvShow tvShowEntity = new TvShow();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos UPDATED");
		tvShow1.setAdvertising("Muertes");
		tvShow1.setCategories(listEntitiesCategories);
		tvShow1.setLongDescription("Description long");
		tvShow1.setRecommendedAge(Byte.parseByte("18"));
		tvShow1.setShortDescription("Description short");
		tvShow1.setYear(Year.of(2012));

		Mockito.when(tvShowRepository.getOne(Mockito.anyLong())).thenReturn(tvShowEntity);
		Mockito.when(tvShowService.updateTvShow(Mockito.any(TvShowRest.class), Mockito.anyLong())).thenReturn(tvShow1);

		mockMvc.perform(patch(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1
				+ RestConstants.RESOURCE_TV_SHOW + RestConstants.RESOURCE_ID, 1)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"advertising\": \"Muertes\",\"categories\": [{\"id\": 1,\"name\": \"ACCION\"}],"
								+ "\"longDescription\": \"Description long\",\"name\": \"Juego de tronos UPDATED\",\"recommendedAge\": 18,"
								+ "\"shortDescription\": \"Description short\",\"year\": 2012}"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id", is(1)))
				.andExpect(jsonPath("$.data.name", is("Juego de tronos UPDATED")));
	}

	@Test
	void testDeleteTvShowById() throws Exception {
		mockMvc.perform(
				delete(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_TV_SHOW
						+ RestConstants.RESOURCE_ID, 1).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

}
