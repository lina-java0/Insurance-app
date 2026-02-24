package org.export.travel.insurance.rest.v1;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.export.travel.insurance.core.services.TravelCalculatePremiumService;
import org.export.travel.insurance.dto.v1.DtoV1Converter;
import org.export.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.export.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.export.travel.insurance.rest.common.TravelCalculatePremiumRequestExecutionTimeLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/v1")
@RequiredArgsConstructor
public class TravelCalculatePremiumControllerV1 {

	private final TravelCalculatePremiumRequestLoggerV1 requestLogger;
	private final TravelCalculatePremiumResponseLoggerV1 responseLogger;
	private final TravelCalculatePremiumRequestExecutionTimeLogger executionTimeLogger;
	private final TravelCalculatePremiumService calculatePremiumService;
	private final DtoV1Converter dtoV1Converter;

	@PostMapping(
			consumes = "application/json",
			produces = "application/json")
	public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelCalculatePremiumResponseV1 response = processRequest(request);
		executionTimeLogger.logExecutionTime(stopwatch);
		return response;
	}

	private TravelCalculatePremiumResponseV1 processRequest(TravelCalculatePremiumRequestV1 request) {
		requestLogger.log(request);
		TravelCalculatePremiumCoreCommand coreCommand = dtoV1Converter.buildCoreCommand(request);
		TravelCalculatePremiumCoreResult coreResult = calculatePremiumService.calculatePremium(coreCommand);
		TravelCalculatePremiumResponseV1 response = dtoV1Converter.buildResponse(coreResult);
		responseLogger.log(response);
		return response;
	}
}