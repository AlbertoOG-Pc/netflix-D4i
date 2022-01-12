package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class TvShowServiceImplTest {

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private TvShowRepository tvShowRepository;

	@InjectMocks
	private TvShowServiceImpl tvShowService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("SERVICE - Get tvShows by category")
	@Test
	void testGetTvShowsByCategory() throws NetflixException {
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("COMEDIA");
		Category category2 = new Category();
		category1.setId(2L);
		category1.setName("Terror");
		TvShow tvShow1 = new TvShow();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos");
		tvShow1.setCategories(Arrays.asList(category1));
		TvShow tvShow2 = new TvShow();
		tvShow2.setId(2L);
		tvShow2.setName("Juego del calamar");
		tvShow2.setCategories(Arrays.asList(category2));

		List<TvShow> tvShowList = new ArrayList<>(Arrays.asList(tvShow1));

		Mockito.when(tvShowRepository.findByCategoriesId(1L)).thenReturn(Optional.of(tvShowList));

		List<TvShowRest> tvShowListResult = tvShowService.getTvShowsByCategory(1L);

		assertThat(tvShowListResult).isNotNull().isNotEmpty().hasSize(1);
		assertThat(tvShowListResult.get(0).getName()).isEqualTo("Juego de tronos");
		assertThat(tvShowListResult.get(0).getCategories()).hasSize(1);
	}

	@DisplayName("SERVICE - Get tvShows by id")
	@Test
	void testGetTvShowById() throws NetflixException {
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("COMEDIA");
		Category category2 = new Category();
		category1.setId(2L);
		category1.setName("Terror");
		TvShow tvShow1 = new TvShow();
		tvShow1.setId(1L);
		tvShow1.setName("Juego de tronos");
		tvShow1.setCategories(Arrays.asList(category1, category2));
		

		Mockito.when(tvShowRepository.findById(1L)).thenReturn(Optional.of(tvShow1));
		Mockito.when(tvShowRepository.findById(2L)).thenReturn(Optional.empty());

		TvShowRest tvShowResult = tvShowService.getTvShowById(1L);

		assertThat(tvShowResult).isNotNull();
		assertThat(tvShowResult.getName()).isEqualTo("Juego de tronos");
		assertThat(tvShowResult.getCategories()).hasSize(2);

		Exception exception = assertThrows(NotFoundException.class, () -> {
			tvShowService.getTvShowById(2L);
		});

		String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testCreateTvShow() throws NetflixException {
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("COMEDIA");
		
		CategoryRest categoryRest1 = new CategoryRest();
		categoryRest1.setId(1L);
		categoryRest1.setName("COMEDIA");
		
		TvShow tvShow = new TvShow();
		tvShow.setId(1L);
		tvShow.setName("Juego de tronos");
		tvShow.setYear(Year.of(2020));
		tvShow.setCategories(Arrays.asList(category1));

		Mockito.when(tvShowRepository.save(Mockito.any(TvShow.class))).thenReturn(tvShow);

		TvShowRest tvShowCreate = new TvShowRest();
		tvShowCreate.setId(1L);
		tvShowCreate.setName("Juego de tronos");
		tvShowCreate.setYear(Year.of(2020));
		tvShowCreate.setCategories(Arrays.asList(categoryRest1));

		TvShowRest tvShowResult = tvShowService.createTvShow(tvShowCreate);
		assertThat(tvShowResult).isNotNull();
		assertThat(tvShowResult.getName()).isEqualTo("Juego de tronos");
		
		
	}

	@Test
	void testUpdateTvShow() throws NetflixException {
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("COMEDIA");
		
		CategoryRest categoryRest1 = new CategoryRest();
		categoryRest1.setId(1L);
		categoryRest1.setName("COMEDIA");
		
		TvShow tvShow = new TvShow();
		tvShow.setId(1L);
		tvShow.setName("Juego de tronos");
		tvShow.setYear(Year.of(2020));
		tvShow.setCategories(Arrays.asList(category1));
		
		TvShow tvShow2 = new TvShow();
		tvShow.setId(1L);
		tvShow.setName("Juego de tronos Editado");
		tvShow.setYear(Year.of(2020));
		tvShow.setCategories(Arrays.asList(category1));

		Mockito.when(tvShowRepository.getOne(1L)).thenReturn(tvShow);
		Mockito.when(tvShowRepository.save(Mockito.any(TvShow.class))).thenReturn(tvShow2);

		TvShowRest tvShowUpdate = new TvShowRest();
		tvShowUpdate.setId(1L);
		tvShowUpdate.setName("Juego de tronos Editado");
		tvShowUpdate.setYear(Year.of(2020));
		tvShowUpdate.setCategories(Arrays.asList(categoryRest1));

		TvShowRest tvShowResult = tvShowService.updateTvShow(tvShowUpdate, 1L);
		assertThat(tvShowResult).isNotNull();
		assertThat(tvShowResult.getName()).isEqualTo("Juego de tronos Editado");
	}

	@Test
	void testDeleteTvShowById() {
		doThrow(new IllegalArgumentException()).when(tvShowRepository).deleteById(100L);

		Exception exception = assertThrows(InternalServerErrorException.class, () -> {
			tvShowService.deleteTvShowById(100L);
		});

		String expectedMessage = ExceptionConstants.INTERNAL_SERVER_ERROR;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
