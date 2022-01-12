package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.services.impl.ChapterServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
class ChapterControllerImplTest {

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private ChapterRepository chapterRepository;

	@Mock
	private ChapterServiceImpl chapterService;

	@InjectMocks
	private ChapterControllerImpl chapterController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(chapterController).build();
		// this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}

	@DisplayName("Test get Chapter by tv show id and season number")
	@Test
	void testGetChaptersByTvShowIdAndSeasonNumber() throws Exception {
		ChapterRest chapter1 = new ChapterRest();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		chapter1.setNumber((short) 20);
		ChapterRest chapter2 = new ChapterRest();
		chapter2.setId(2L);
		chapter2.setName("Chapter 2");
		chapter2.setNumber((short) 21);
		List<ChapterRest> listChapter = new ArrayList<>(Arrays.asList(chapter1, chapter2));

		Mockito.when(chapterService.getChaptersByTvShowIdAndSeasonNumber(1L, (short) 1)).thenReturn(listChapter);

		mockMvc.perform(get(
				RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CHAPTER, 1L, 1))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.length()", is(2))).andExpect(jsonPath("$.data[0].name", is("Chapter 1")))
				.andExpect(jsonPath("$.data[1].id", is(2)));
	}

	@Test
	void testGetChapterByTvShowIdAndSeasonNumberAndChapterNumber() throws Exception {
		ChapterRest chapter1 = new ChapterRest();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		chapter1.setNumber((short) 20);

		Mockito.when(chapterService.getChapterByTvShowIdAndSeasonNumberAndChapterNumber(1L, (short) 1, (short) 1))
				.thenReturn(chapter1);

		mockMvc.perform(get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1
				+ RestConstants.RESOURCE_CHAPTER + RestConstants.RESOURCE_NUMBER, 1L, 1, 1))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.name", is("Chapter 1"))).andExpect(jsonPath("$.data.id", is(1)));
	}

	@Test
	void testUpdateChaptersById() throws Exception {
		ChapterRest chapter1 = new ChapterRest();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		chapter1.setNumber((short) 20);

		Mockito.when(chapterService.updateChapters(Mockito.any(Long.class), Mockito.any(short.class),
				Mockito.any(ChapterRest.class), Mockito.any(Short.class))).thenReturn(chapter1);

		mockMvc.perform(
				patch(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CHAPTER
						+ RestConstants.RESOURCE_ID, 1L, 1, 1).contentType(MediaType.APPLICATION_JSON_UTF8)
								.content("{\"name\": \"Chapter1\", \"number\": 20}"))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.name", is("Chapter 1"))).andExpect(jsonPath("$.data.id", is(1)));
	}

}
