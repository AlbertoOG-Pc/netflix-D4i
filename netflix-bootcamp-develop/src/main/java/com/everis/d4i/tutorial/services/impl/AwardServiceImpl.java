package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.repositories.AwardsRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.AwardService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class AwardServiceImpl implements AwardService {
	@Autowired
	AwardsRepository awardsRepository;
	
	@Autowired
	TvShowRepository tvShowRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<TvShowAwardRest> getAwardsByTvShowId(Long tvShowId) throws NetflixException {
		List<TvShowAwardRest> listAwards = new ArrayList<>();
				
		listAwards = awardsRepository.findByTvShow(
					tvShowRepository.findById(tvShowId).orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW))
				)
				.stream()
				.map(award -> modelMapper.map(award, TvShowAwardRest.class))
				.collect(Collectors.toList());
		
		return listAwards;
	}

}
