package com.accenture.java.msopentdmaven.repository.productMappingApi;

import com.accenture.java.msopentdmaven.exception.ApiErrorResponse;
import com.accenture.java.msopentdmaven.exception.BadRequestException;
import com.accenture.java.msopentdmaven.repository.productMappingApi.dto.ProductInfoResponse;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class ProductInfoRepository {

    private final WebClient webClient;

    private final Logger log = LoggerFactory.getLogger(ProductInfoRepository.class);

    @Value("${downstreams.services.productMappingEndpoint}")
    private String productMappingEndpoint;

    @Value("${downstreams.services.productMappingPath}")
    private String productMappingPath;

    public ProductInfoRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductInfoResponse getProductId(String productCode, String correlationId) {
        return webClient
                .get()
                .uri(productMappingEndpoint + productMappingPath, productCode)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("correlation-id", correlationId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::handleDownstreamError)
                .bodyToMono(ProductInfoResponse.class)
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
