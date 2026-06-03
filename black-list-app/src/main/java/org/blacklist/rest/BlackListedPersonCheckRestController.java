package org.blacklist.rest;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import org.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.blacklist.core.services.BlackListedPersonService;
import org.blacklist.dto.BlackListedPersonCheckRequest;
import org.blacklist.dto.BlackListedPersonCheckResponse;
import org.blacklist.dto.DtoConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blacklist/person/check")
@RequiredArgsConstructor
public class BlackListedPersonCheckRestController {

	private final BlackListedPersonRequestLogger requestLogger;
	private final BlackListedPersonResponseLogger responseLogger;
	private final RestRequestExecutionTimeLogger executionTimeLogger;
	private final BlackListedPersonService blackListedPersonService;
	private final DtoConverter dtoConverter;

	@PostMapping(
			consumes = "application/json",
			produces = "application/json")
	public BlackListedPersonCheckResponse checkPerson(@RequestBody BlackListedPersonCheckRequest request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		BlackListedPersonCheckResponse response = processRequest(request);
		executionTimeLogger.logExecutionTime(stopwatch);
		return response;
	}

	private BlackListedPersonCheckResponse processRequest(BlackListedPersonCheckRequest request) {
		requestLogger.log(request);
		BlackListedPersonCoreCommand coreCommand = dtoConverter.buildCoreCommand(request);
		BlackListedPersonCoreResult coreResult = blackListedPersonService.checkPerson(coreCommand);
		BlackListedPersonCheckResponse response = dtoConverter.buildResponse(coreResult);
		responseLogger.log(response);
		return response;
	}
}