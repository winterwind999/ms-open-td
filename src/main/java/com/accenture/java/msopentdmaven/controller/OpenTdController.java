package com.accenture.java.msopentdmaven.controller;

import com.accenture.java.msopentdmaven.api.CreateTdAccountRequest;
import com.accenture.java.msopentdmaven.api.CreateTdAccountResponse;
import com.accenture.java.msopentdmaven.service.CreateTdAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class OpenTdController {

	private final CreateTdAccountService createTdAccountService;

	@Autowired
	public OpenTdController(CreateTdAccountService createTdAccountService) {
		this.createTdAccountService = createTdAccountService;
	}

	@PostMapping("/ms-open-td/openAccount")
	public ResponseEntity<CreateTdAccountResponse> createTdAccount(@RequestHeader HttpHeaders httpHeaders,
			@RequestBody CreateTdAccountRequest createTdAccountRequest) {

		CreateTdAccountResponse createTdAccountResponse = createTdAccountService.createTdAccount(createTdAccountRequest,
				httpHeaders);

		return new ResponseEntity<>(createTdAccountResponse, HttpStatus.OK);
	}
}
