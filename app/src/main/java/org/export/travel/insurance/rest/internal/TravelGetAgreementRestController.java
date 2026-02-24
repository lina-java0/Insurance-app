package org.export.travel.insurance.rest.internal;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.export.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.export.travel.insurance.core.services.TravelGetAgreementService;
import org.export.travel.insurance.dto.internal.GetAgreementDtoConverter;
import org.export.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.export.travel.insurance.rest.common.TravelCalculatePremiumRequestExecutionTimeLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/internal/agreement")
@RequiredArgsConstructor
public class TravelGetAgreementRestController {

	private final TravelGetAgreementRequestLogger requestLogger;
	private final TravelGetAgreementResponseLogger responseLogger;
	private final TravelCalculatePremiumRequestExecutionTimeLogger executionTimeLogger;
	private final TravelGetAgreementService travelGetAgreementService;
	private final GetAgreementDtoConverter dtoConverter;

	@GetMapping(
			path = "/{uuid}",
			produces = "application/json")
	public TravelGetAgreementResponse getAgreementResponse(@PathVariable("uuid") String uuid) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelGetAgreementResponse response = processRequest(uuid);
		executionTimeLogger.logExecutionTime(stopwatch);
		return response;
	}

	private TravelGetAgreementResponse processRequest(String uuid) {
		requestLogger.log(uuid);

		TravelGetAgreementCoreCommand coreCommand = new TravelGetAgreementCoreCommand(uuid);
		TravelGetAgreementCoreResult coreResult = travelGetAgreementService.getAgreement(coreCommand);
		TravelGetAgreementResponse response = dtoConverter.buildResponse(coreResult);

		responseLogger.log(response);
		return response;
	}
}