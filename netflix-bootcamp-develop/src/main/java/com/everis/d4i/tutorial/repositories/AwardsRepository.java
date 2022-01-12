package com.everis.d4i.tutorial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.entities.TvShowAward;


@Repository
public interface AwardsRepository extends JpaRepository<TvShowAward, Long>{
	List<TvShowAward> findByTvShow(TvShow tvShow);
}
