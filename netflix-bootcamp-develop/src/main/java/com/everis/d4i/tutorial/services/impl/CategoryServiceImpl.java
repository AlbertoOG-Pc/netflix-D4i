package com.everis.d4i.tutorial.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.services.CategoryService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
//@Validated TODO: Mirar por que falla.
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private Set<ConstraintViolation<CategoryRest>> violations;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryRest> getCategories() throws NetflixException {

		return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryRest.class))
				.collect(Collectors.toList());

	}

	public CategoryRest createCategories(@Valid CategoryRest categoryRest) throws NetflixException {
		Category category = new Category();
		try {
			violations = validator.validate(categoryRest);
			if (!violations.isEmpty()) {
				throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
			}
			category.setName(categoryRest.getName());
			category = categoryRepository.save(category);
		} catch (final Exception e) {
			LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, e);
			throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
		}
		return modelMapper.map(category, CategoryRest.class);
	}

}
