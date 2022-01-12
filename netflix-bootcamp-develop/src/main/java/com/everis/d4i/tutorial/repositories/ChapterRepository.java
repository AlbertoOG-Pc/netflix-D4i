package com.everis.d4i.tutorial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everis.d4i.tutorial.entities.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

	Optional<List<Chapter>> findBySeasonTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber);

	Optional<Chapter> findBySeasonTvShowIdAndSeasonNumberAndNumber(Long tvShowId, short seasonNumber,
			short chapterNumber);
	
	List<Chapter> findByActorsId(Long actorId);
}
