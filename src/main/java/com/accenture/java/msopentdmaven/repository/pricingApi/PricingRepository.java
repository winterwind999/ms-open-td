package com.accenture.java.msopentdmaven.repository.pricingApi;

import com.accenture.java.msopentdmaven.exception.ApiErrorResponse;
import com.accenture.java.msopentdmaven.exception.BadRequestException;
import com.accenture.java.msopentdmaven.repository.pricingApi.dto.ValidatePricingRequest;
import com.accenture.java.msopentdmaven.repository.pricingApi.dto.ValidatePricingResponse;
import com.accenture.java.msopentdmaven.repository.productMappingApi.ProductInfoRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class PricingRepository {

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(ProductInfoRepository.class);

    @Value("${downstreams.services.pricingEndpoint}")
    private String pricingEndpoint;

    @Value("${downstreams.services.validatePricingPath}")
    private String validatePricingPath;

    @Value("${downstreams.services.getPricingInfoPath}")
    private String getPricingInfoPath;

    public PricingRepository(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    // TODO: complete method implementation
    public ValidatePricingResponse validatePricing(
            ValidatePricingRequest validatePricingRequest, String correlationId
    ) {
        return webClient
                .post()
                .uri(pricingEndpoint + validatePricingPath)
                .body(BodyInserters.fromValue(validatePricingRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::handleDownstreamError)
                .bodyToMono(ValidatePricingResponse.class)
                .doOnError(error -> log.error("message='Unexpected downstream response!'"))
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
