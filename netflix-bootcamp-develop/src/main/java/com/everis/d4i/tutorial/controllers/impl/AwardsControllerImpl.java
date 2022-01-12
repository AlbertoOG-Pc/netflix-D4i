package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.AwardsController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.TvShowAwardRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.AwardService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// description deprecated, in swagger 3 use @tag
@Api(tags = "Awards URIs", description = "Provides application Awards API's")
@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_AWARDS)
public class AwardsControllerImpl implements AwardsController {

	@Autowired
	private AwardService awardService;
	
	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Operation for get awards of Tvshow by tvshow id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CommonConstants.OK, response = TvShowAwardRest.class),
            @ApiResponse(code = 404, message = ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR) })
	public NetflixResponse<List<TvShowAwardRest>> getAwardsByTvShowId(@PathVariable Long tvShowId) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				awardService.getAwardsByTvShowId(tvShowId));
	}

}
