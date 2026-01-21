package com.accenture.java.msopentdmaven.service;

import com.accenture.java.msopentdmaven.api.CreateTdAccountRequest;
import com.accenture.java.msopentdmaven.api.CreateTdAccountResponse;
import com.accenture.java.msopentdmaven.api.DepositDetails;
import com.accenture.java.msopentdmaven.exception.BadRequestException;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.BankLedgerRepository;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerDepositDetails;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerRequest;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerResponse;
import com.accenture.java.msopentdmaven.repository.database.CreateTdRequestsRepository;
import com.accenture.java.msopentdmaven.repository.database.entity.CreateTdRequest;
import com.accenture.java.msopentdmaven.repository.database.type.CreateTdRequestStatus;
import com.accenture.java.msopentdmaven.repository.pricingApi.PricingRepository;
import com.accenture.java.msopentdmaven.repository.pricingApi.dto.ValidatePricingRequest;
import com.accenture.java.msopentdmaven.repository.pricingApi.dto.ValidatePricingResponse;
import com.accenture.java.msopentdmaven.repository.productMappingApi.ProductInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// FIX TASK 2
// ADDED @Service annotation
@Service
public class CreateTdAccountService {

	private final ProductInfoRepository productInfoRepository;

	private final PricingRepository pricingRepository;

	private final CreateTdRequestsRepository createTdRequestsRepository;

	private final BankLedgerRepository bankLedgerRepository;

	@Autowired
	public CreateTdAccountService(ProductInfoRepository productInfoRepository, PricingRepository pricingRepository,
			CreateTdRequestsRepository createTdRequestsRepository, BankLedgerRepository bankLedgerRepository) {
		this.productInfoRepository = productInfoRepository;
		this.pricingRepository = pricingRepository;
		this.createTdRequestsRepository = createTdRequestsRepository;
		this.bankLedgerRepository = bankLedgerRepository;
	}

	public CreateTdAccountResponse createTdAccount(CreateTdAccountRequest createTdAccountRequest, HttpHeaders headers) {
		String correlationId = headers.getFirst("correlation-id");
		CreateTdRequest createTdRequest = createOpenAccountRequestEntry(createTdAccountRequest, correlationId);

		updateTdAccountRequestStatus(createTdRequest, CreateTdRequestStatus.GET_PRODUCT);
		String productId = getProductId(createTdAccountRequest.getProductCode(), correlationId);

		updateTdAccountRequestProductId(createTdRequest, productId);

		updateTdAccountRequestStatus(createTdRequest, CreateTdRequestStatus.VALIDATE_PRICING);

		validatePricingInfo(createTdAccountRequest.getDepositDetails(), productId, correlationId);

		updateTdAccountRequestStatus(createTdRequest, CreateTdRequestStatus.LEDGER_CALL);
		String accountNumber = createAccountOnLedger(createTdAccountRequest, productId);
		updateTdAccountRequestStatus(createTdRequest, CreateTdRequestStatus.SUCCESSFUL);

		return new CreateTdAccountResponse(accountNumber);
	}

	private void updateTdAccountRequestProductId(CreateTdRequest createTdRequest, String productId) {
		// ADDITIONAL FIX added productId
		createTdRequest.setProductId(productId);
		this.createTdRequestsRepository.save(createTdRequest);
	}

	private void updateTdAccountRequestStatus(CreateTdRequest createTdRequest, CreateTdRequestStatus status) {
		createTdRequest.setStatus(status.name());
		this.createTdRequestsRepository.save(createTdRequest);
	}

	private String getProductId(String productCode, String correlationId) {
		return productInfoRepository.getProductId(productCode, correlationId).getProductId();
	}

	private CreateTdRequest createOpenAccountRequestEntry(CreateTdAccountRequest createTdAccountRequest,
			String correlationId) {
		CreateTdRequest request = createTdRequestsRepository.findById(correlationId).orElse(null);

		if (request != null) {
			// FIX TASK 5
			// replace "HttpStatus.CREATED.value()" with "HttpStatus.BAD_REQUEST.value()"
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "Correlation id already exists.",
					Collections.emptyMap());
		}

		String requestPayloadStr = requestPayloadToString(createTdAccountRequest);

		// FIX TASK 4
 		// replace "fix me" with correlationId
		CreateTdRequest createTdRequest = new CreateTdRequest(correlationId, requestPayloadStr);

		return createTdRequestsRepository.save(createTdRequest);
	}

	private String requestPayloadToString(CreateTdAccountRequest createTdAccountRequest) {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.writeValueAsString(createTdAccountRequest);
		} catch (JsonProcessingException e) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), e.getMessage(), Collections.emptyMap());
		}
	}

	private ValidatePricingRequest generateValidatePricingRequest(DepositDetails depositDetails, String productId) {
		String interestRate = depositDetails.getInterestRate();
		String term = depositDetails.getTerm();

		return new ValidatePricingRequest(productId, interestRate, term);
	}

	private void validatePricingInfo(DepositDetails depositDetails, String productId, String correlationId) {
		ValidatePricingRequest validatePricingRequest = generateValidatePricingRequest(depositDetails, productId);
		ValidatePricingResponse validatePricingResponse = pricingRepository.validatePricing(validatePricingRequest,
				correlationId);

		if (!validatePricingResponse.getIsValid()) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), validatePricingResponse.getNotValidReason(),
					Collections.emptyMap());
		}
	}

	private String createAccountOnLedger(CreateTdAccountRequest createTdAccountRequest, String productId) {
		BankLedgerRequest bankLedgerRequest = generateBankLedgerRequest(createTdAccountRequest, productId);
		BankLedgerResponse bankLedgerResponse = this.bankLedgerRepository.saveAccount(bankLedgerRequest);

		return bankLedgerResponse.getAccountNumber();
	}

	private BankLedgerRequest generateBankLedgerRequest(CreateTdAccountRequest createTdAccountRequest,
			String productId) {
		BankLedgerDepositDetails depositDetails = createTdAccountRequest.getDepositDetails()
				.generateBankLedgerDetailsDetails();

		return new BankLedgerRequest(productId, depositDetails, createTdAccountRequest.getMaturityDetails());
	}
}
