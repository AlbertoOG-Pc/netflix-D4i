package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Year;
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

import com.everis.d4i.tutorial.entities.Awards;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.entities.TvShowAward;
import com.everis.d4i.tutorial.entities.composite.TvShowAwardComposite;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.repositories.AwardsRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class AwardServiceImplTest {

	@Autowired
	//private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	@Mock
	private AwardsRepository awardsRepository;
	
	@Mock
	private TvShowRepository tvShowRepository;
	
	@InjectMocks
	private AwardServiceImpl awardService;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		//this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}
	
	@DisplayName("SERVICE - Get all awards for tvShow")
	@Test
	void testGetAwardsByTvShowId() throws NetflixException {
		TvShow tvshow = new TvShow();
		tvshow.setId(1L);
		tvshow.setName("Juego de tronos");
		Optional<TvShow> opTvShow = Optional.of(tvshow);
		
		Awards award1 = new Awards();
		award1.setId(1L);
		award1.setName("Mejor drama");
		
		TvShowAwardComposite primaryKey1 = new TvShowAwardComposite();
		primaryKey1.setAward(1);
		primaryKey1.setTvShow(1);
		primaryKey1.setYear(Year.of(2019));
		
		TvShowAwardComposite primaryKey2 = new TvShowAwardComposite();
		primaryKey2.setAward(1);
		primaryKey2.setTvShow(1);
		primaryKey2.setYear(Year.of(2020));
		
		
		List<TvShowAward> listAwards = new ArrayList<>();
		TvShowAward tvShowAward1 = new TvShowAward();
		tvShowAward1.setTvShow(tvshow);
		tvShowAward1.setAward(award1);
		tvShowAward1.setPrimaryKey(primaryKey1);
		TvShowAward tvShowAward2 = new TvShowAward();
		tvShowAward2.setTvShow(tvshow);
		tvShowAward2.setAward(award1);
		tvShowAward2.setPrimaryKey(primaryKey2);
		
		listAwards.add(tvShowAward1);
		listAwards.add(tvShowAward2);
		
		Mockito.when(tvShowRepository.findById(1L)).thenReturn(opTvShow);
		Mockito.when(awardsRepository.findByTvShow(tvshow)).thenReturn(listAwards);
		

		List<TvShowAwardRest> listAwardsRest = awardService.getAwardsByTvShowId(1L);
		assertThat(listAwardsRest).isNotNull().isNotEmpty().hasSize(2);
		assertThat(listAwardsRest.get(0).getYear()).isEqualTo(Year.of(2019));
		assertThat(listAwardsRest.get(0).getAward().getName()).isEqualTo("Mejor drama");
		assertThat(listAwardsRest.get(0).getTvShow().getName()).isEqualTo("Juego de tronos");
	}

}
