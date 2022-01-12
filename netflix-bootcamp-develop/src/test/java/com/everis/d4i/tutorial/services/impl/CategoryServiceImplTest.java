package com.everis.d4i.tutorial.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

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

import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceImplTest {

	@Autowired
	//private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		//this.mockMvc = MockMvcBuilders.standaloneSetup(actorService).build();
	}
	
	@DisplayName("SERVICE - Get all categories")
	@Test
	void testGetCategories() throws Exception {
		List<Category> categories = new ArrayList<>();
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("ACCION");
		Category category2 = new Category();
		category2.setId(2L);
		category2.setName("COMEDIA");
		categories.add(category1);
		categories.add(category2);
				
		
		Mockito.when(categoryRepository.findAll()).thenReturn(categories);
		
		List<CategoryRest> listCategories = categoryService.getCategories();
		assertThat(listCategories).isNotNull().isNotEmpty().hasSize(2);
		assertThat(listCategories.get(0).getName()).isEqualTo("ACCION");
		assertThat(listCategories.get(1).getName()).isEqualTo("COMEDIA");
		
	}

	@DisplayName("SERVICE - Create categorie")
	@Test
	void testCreateCategories() throws Exception {
		Category category = new Category();
		category.setId(1L);
		category.setName("ACCION");
		
		CategoryRest category1 = new CategoryRest();
		category1.setName("ACCION");
		
		Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
		Mockito.when(categoryRepository.save(new Category(anyLong(), ""))).thenThrow(new NullPointerException("aa"));  
		Mockito.when(categoryRepository.save(new Category(anyLong(), null))).thenThrow(new NullPointerException("aa"));
		
		CategoryRest categorySaved = categoryService.createCategories(category1);
		CategoryRest categoryError = new CategoryRest();
		categoryError.setId(2L);
		categoryError.setName("");
		
		assertThat(categorySaved).isNotNull();
		assertThat(categorySaved.getId()).isEqualTo(category.getId());
		assertThat(categorySaved.getName()).isEqualTo(category.getName());
		
		Exception exception = assertThrows(InternalServerErrorException.class, () -> {
			categoryService.createCategories(categoryError);
	    });

	    String expectedMessage = ExceptionConstants.INTERNAL_SERVER_ERROR;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
