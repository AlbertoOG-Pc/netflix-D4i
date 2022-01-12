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

import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class ChapterServiceImplTest {

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private ChapterRepository chapterRepository;

	@InjectMocks
	private ChapterServiceImpl chapterService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("SERVICE - Get Chapter by tvShow id and Season Number")
	@Test
	void testGetChaptersByTvShowIdAndSeasonNumber() throws NetflixException {
		Chapter chapter1 = new Chapter();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		Chapter chapter2 = new Chapter();
		chapter2.setId(2L);
		chapter2.setName("Chapter 2");
		List<Chapter> chapters = new ArrayList<>(Arrays.asList(chapter1, chapter2));
		Optional<List<Chapter>> opChapters = Optional.of(chapters);

		Mockito.when(chapterRepository.findBySeasonTvShowIdAndSeasonNumber(1L, (short) 1)).thenReturn(opChapters);

		List<ChapterRest> chaptersListResult = chapterService.getChaptersByTvShowIdAndSeasonNumber(1L, (short) 1);

		assertThat(chaptersListResult).isNotNull().isNotEmpty().hasSize(2);
		assertThat(chaptersListResult.get(0).getName()).isEqualTo("Chapter 1");
		assertThat(chaptersListResult.get(1).getId()).isEqualTo(2L);
	}

	@DisplayName("SERVICE - Get Chapter by tvShow id and Season number and chapter number")
	@Test
	void testGetChapterByTvShowIdAndSeasonNumberAndChapterNumber() throws NetflixException {
		Chapter chapter1 = new Chapter();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		chapter1.setNumber((short) 1);
		Optional<Chapter> optinalChapter = Optional.of(chapter1);
		Optional<Chapter> optinalChapter2 = Optional.empty();
		Mockito.when(chapterRepository.findBySeasonTvShowIdAndSeasonNumberAndNumber(1L, (short) 1, (short) 1))
				.thenReturn(optinalChapter);
		Mockito.when(chapterRepository.findBySeasonTvShowIdAndSeasonNumberAndNumber(2L, (short) 1, (short) 1))
				.thenReturn(optinalChapter2);

		ChapterRest chapterResult = chapterService.getChapterByTvShowIdAndSeasonNumberAndChapterNumber(1L, (short) 1,
				(short) 1);

		assertThat(chapterResult).isNotNull();
		assertThat(chapterResult.getName()).isEqualTo("Chapter 1");

		Exception exception = assertThrows(NotFoundException.class, () -> {
			chapterService.getChapterByTvShowIdAndSeasonNumberAndChapterNumber(2L, (short) 1, (short) 1);
		});

		String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@DisplayName("SERVICE - Update chapter")
	@Test
	void testUpdateChapters() throws NetflixException {
		Chapter chapter1 = new Chapter();
		chapter1.setId(1L);
		chapter1.setName("Chapter 1");
		chapter1.setDuration((short) 60);
		chapter1.setNumber((short) 1);
		Chapter chapter2 = new Chapter();
		chapter2.setId(2L);
		chapter2.setName("Chapter 2");
		chapter2.setDuration((short) 60);
		chapter2.setNumber((short) 2);

		List<Chapter> chapters = new ArrayList<>(Arrays.asList(chapter1, chapter2));

		ChapterRest chapterEdited = new ChapterRest();
		chapterEdited.setName("Chapter 2");
		chapterEdited.setDuration((short) 50);
		chapterEdited.setNumber((short) 2);

		Chapter chapterEditedEntity = new Chapter();
		chapterEditedEntity.setId(2L);
		chapterEditedEntity.setName("Chapter 2");
		chapterEditedEntity.setDuration((short) 50);
		chapterEditedEntity.setNumber((short) 2);
		
		Optional<List<Chapter>> opChapters = Optional.of(chapters);

		Mockito.when(chapterRepository.findBySeasonTvShowIdAndSeasonNumber(1L, (short) 1)).thenReturn(opChapters);
		Mockito.when(chapterRepository.findBySeasonTvShowIdAndSeasonNumberAndNumber(1L, (short) 1, (short)2)).thenReturn(Optional.of(chapter1));
		Mockito.when(chapterRepository.getOne(1L)).thenReturn(chapterEditedEntity);
		Mockito.when(chapterRepository.save(Mockito.any(Chapter.class))).thenReturn(chapterEditedEntity);

		ChapterRest chapterFinal = chapterService.updateChapters(1L, (short) 1, chapterEdited, (short)2);

		assertThat(chapterFinal).isNotNull();
		assertThat(chapterFinal.getId()).isEqualTo(2L);
		assertThat(chapterFinal.getDuration()).isEqualTo((short)50);
		assertThat(chapterFinal.getName()).isEqualTo("Chapter 2");
		
		Exception exception = assertThrows(NotFoundException.class, () -> {
			chapterService.updateChapters(1L, (short) 1, chapterEdited, (short)3);
		});

		String expectedMessage = ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}
