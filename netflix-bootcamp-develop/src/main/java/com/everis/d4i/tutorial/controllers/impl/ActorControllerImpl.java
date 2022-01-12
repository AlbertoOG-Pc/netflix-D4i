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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.ActorController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.Filmografy.ActorFilmografyRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

//description deprecated, in swagger 3 use @tag
@Api(tags = "Actor URIs", description = "Provides application Actor API's")
@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ACTOR)
public class ActorControllerImpl implements ActorController {
	
	@Autowired
	private ActorService actorService;
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get all actors registered")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRest.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<List<ActorRest>> getActors() throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActors());
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get one actors registered by your ID")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRest.class),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<ActorRest> getActorById(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActorById(id));
	}
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for register a new Actor")
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = CommonConstants.CREATED, response = ActorRest.class),
            @ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<ActorRest> createActor(
			@ApiParam(value = RestConstants.PARAMETER_ACTOR, required = true) @RequestBody final ActorRest actorRest)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.CREATED, String.valueOf(HttpStatus.CREATED), CommonConstants.OK,
				actorService.createActor(actorRest));
	}
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for update name of actor registered")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = ActorRest.class),
            @ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<ActorRest> updateActorNameById(@RequestBody @Valid final ActorRest actorRest, @PathVariable Long id ) 
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.updateActorName(actorRest, id));
	}
	
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@PatchMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for delete actor registered")
	@ApiResponses(value = {
            @ApiResponse(code = 204, message = CommonConstants.NO_CONTENT),
            @ApiResponse(code = 401, message = ExceptionConstants.UNAUTHORIZED_ERROR),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<ActorRest> deleteActorById(@PathVariable Long id) throws NetflixException {
		actorService.deleteActorById(id);
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK);
	}
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_FILMOGRAPHY + RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get filmografy by actor ID")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = NetflixResponse.class),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_ACTOR),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<ActorFilmografyRest> getActorFilmography(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActorFilmography(id));
	}
}
