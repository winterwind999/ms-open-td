package com.accenture.java.msopentdmaven.repository.bankLedgerApi;

import com.accenture.java.msopentdmaven.exception.ApiErrorResponse;
import com.accenture.java.msopentdmaven.exception.BadRequestException;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerRequest;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class BankLedgerRepository {

    private final WebClient webClient;

    @Value("${downstreams.services.bankLedgerMappingEndpoint}")
    private String bankLedgerMappingEndpoint;

    @Value("${downstreams.services.bankLedgerMappingPath}")
    private String bankLedgerMappingPath;

    public BankLedgerRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    public BankLedgerResponse saveAccount(BankLedgerRequest bankLedgerRequest) {
        return webClient
                .post()
                .uri(bankLedgerMappingEndpoint + bankLedgerMappingPath)
                .body(BodyInserters.fromValue(bankLedgerRequest))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::handleDownstreamError)
                .bodyToMono(BankLedgerResponse.class)
                .block();
    }

    private Mono<Throwable> handleDownstreamError(ClientResponse clientResponse) {
        final HttpStatus httpStatus = clientResponse.statusCode();
        ServiceException unknownException = new ServiceException("Unknown downstream error.");
        return clientResponse
                .bodyToMono(ApiErrorResponse.class)
                .flatMap(apiError -> {
                    Exception exception;
                    switch (httpStatus) {
                        case BAD_REQUEST:
                        case INTERNAL_SERVER_ERROR:
                            exception = new BadRequestException(
                                    apiError.getErrorCode(),
                                    apiError.getErrorMessage(),
                                    apiError.getErrorDetails()
                            );
                            break;
                        default:
                            exception = unknownException;
                            break;
                    }
                    return Mono.error(exception);
                });
    }
}
