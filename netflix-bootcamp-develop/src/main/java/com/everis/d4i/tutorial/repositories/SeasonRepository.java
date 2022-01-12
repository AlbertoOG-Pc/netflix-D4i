package com.everis.d4i.tutorial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everis.d4i.tutorial.entities.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

	Optional<List<Season>> findByTvShowId(Long tvShowId);

	Optional<Season> findByTvShowIdAndNumber(Long tvShowId, short seasonNumber);
	
	Season findByChaptersId(Long chapterId);

}
