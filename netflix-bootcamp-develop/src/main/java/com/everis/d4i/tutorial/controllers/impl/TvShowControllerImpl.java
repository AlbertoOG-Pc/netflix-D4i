package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.TvShowController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//description deprecated, in swagger 3 use @tag
@Api(tags = "Tvshow URIs", description = "Provides application Tvshow API's")
@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_TV_SHOW)
public class TvShowControllerImpl implements TvShowController {

	@Autowired
	private TvShowService tvShowService;

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get list of Tv show with indicated category by category Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = CommonConstants.OK, response = TvShowRest.class),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<List<TvShowRest>> getTvShowsByCategory(@RequestParam Long categoryId)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowsByCategory(categoryId));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get Tv show by Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = CommonConstants.OK, response = TvShowRest.class),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<TvShowRest> getTvShowById(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowById(id));
	}
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for register a new Tv show")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = CommonConstants.CREATED, response = TvShowRest.class),
			@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<TvShowRest> createTvShow(
			@ApiParam(value = RestConstants.PARAMETER_TV_SHOW, required = true) @RequestBody @Valid final TvShowRest tvShowRest)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.CREATED), CommonConstants.OK,
				tvShowService.createTvShow(tvShowRest));
	}
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for register a new Tv show")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = CommonConstants.CREATED, response = TvShowRest.class),
			@ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<TvShowRest> updateTvShowById(@RequestBody @Valid final TvShowRest tvShowRest, @PathVariable Long id ) 
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.updateTvShow(tvShowRest, id));
	}
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> deleteTvShowById(@PathVariable Long id) throws NetflixException {
		tvShowService.deleteTvShowById(id);
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK);
	}


}
