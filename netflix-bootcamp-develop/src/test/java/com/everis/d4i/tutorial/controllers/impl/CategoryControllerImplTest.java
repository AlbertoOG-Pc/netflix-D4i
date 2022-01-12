package com.everis.d4i.tutorial.controllers.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.services.impl.CategoryServiceImpl;
import com.everis.d4i.tutorial.utils.constants.RestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(MockitoJUnitRunner.class)
class CategoryControllerImplTest {

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryServiceImpl categoryService;

	@InjectMocks
	private CategoryControllerImpl categorycontroller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(categorycontroller).build();
	}

	@DisplayName("Test for get all category")
	@Test
	void testGetCategories() throws Exception {
		CategoryRest category1 = new CategoryRest();
		category1.setName("COMEDIA");
		category1.setId(1L);
		CategoryRest category2 = new CategoryRest();
		category2.setName("ACCION");
		category2.setId(2L);
		List<CategoryRest> listCategory = new ArrayList<>(Arrays.asList(category1, category2));

		Mockito.when(categoryService.getCategories()).thenReturn(listCategory);

		mockMvc.perform(
				get(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CATEGORY))
				// Validar respuesta
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// Comprobar resultados
				.andExpect(jsonPath("$.data.length()", is(2))).andExpect(jsonPath("$.data[1].name", is("ACCION")))
				.andExpect(jsonPath("$.data[0].id", is(1)));
	}

	@Test
	void testCreateCategory() throws Exception {
		
		CategoryRest categorySaved = new CategoryRest();
		categorySaved.setId(1L);
		categorySaved.setName("COMEDIA");
		
		Mockito.when(categoryService.createCategories(Mockito.any(CategoryRest.class))).thenReturn(categorySaved);
		
		mockMvc.perform(
				post(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CATEGORY)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\": \"COMEDIA\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id", is(1)))
				.andExpect(jsonPath("$.data.name", is("COMEDIA")));
			}

}
