package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

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

import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class SeasonServiceImplTest {

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private SeasonRepository seasonRepository;

	@InjectMocks
	private SeasonServiceImpl seasonService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("SERVICE - Get Seasons by TvShow")
	@Test
	void testGetSeasonsByTvShow() throws NetflixException {
		Season season1 = new Season();
		season1.setId(1L);
		season1.setName("Temporada 1");
		season1.setNumber((short)1);
		Season season2 = new Season();
		season2.setId(2L);
		season2.setName("Temporada 2");
		season2.setNumber((short)2);
		
		List<Season> seasons = new ArrayList<>(Arrays.asList(season1,season2));
		Mockito.when(seasonRepository.findByTvShowId(1L)).thenReturn(Optional.of(seasons));
		
		List<SeasonRest> listSeasons = seasonService.getSeasonsByTvShow(1L);
		assertThat(listSeasons).isNotNull().isNotEmpty().hasSize(2);
		assertThat(listSeasons.get(0).getName()).isEqualTo("Temporada 1");
		assertThat(listSeasons.get(1).getName()).isEqualTo("Temporada 2");
		
	}

	@DisplayName("SERVICE - Get Season by TvShow and season number")
	@Test
	void testGetSeasonByTvShowIdAndSeasonNumber() throws NetflixException {
		Season season1 = new Season();
		season1.setId(1L);
		season1.setName("Temporada 1");
		season1.setNumber((short)1);
		
		Optional<Season> optinalSeason = Optional.of(season1);
		Optional<Season> optinalSeason2 = Optional.empty();
		Mockito.when(seasonRepository.findByTvShowIdAndNumber(1L, (short)1)).thenReturn(optinalSeason);
		Mockito.when(seasonRepository.findByTvShowIdAndNumber(2L, (short)1)).thenReturn(optinalSeason2);
		
		SeasonRest season = seasonService.getSeasonByTvShowIdAndSeasonNumber(1L, (short)1);

		assertThat(season).isNotNull();
		assertThat(season.getName()).isEqualTo("Temporada 1");
		
		Exception exception = assertThrows(NotFoundException.class, () -> {
			seasonService.getSeasonByTvShowIdAndSeasonNumber(2L, (short)1);
		});

		String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_SEASON;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		
		
		
	}

}
