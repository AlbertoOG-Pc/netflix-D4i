package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.CategoryController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.CategoryRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.CategoryService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//description deprecated, in swagger 3 use @tag
@Api(tags = "Category URIs", description = "Provides application Category API's")
@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_CATEGORY)
public class CategoryControllerImpl implements CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get all categories registered")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = CategoryRest.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<List<CategoryRest>> getCategories() throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				categoryService.getCategories());
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for register a new category")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = CommonConstants.CREATED, response = CategoryRest.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<CategoryRest> createCategory(
			@ApiParam(value = RestConstants.PARAMETER_CATEGORY, required = true) @RequestBody @Valid final CategoryRest categoryRest)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.CREATED, String.valueOf(HttpStatus.CREATED), CommonConstants.OK,
				categoryService.createCategories(categoryRest));
	}

}
