package com.accenture.java.msopentdmaven.repository.productMappingApi.dto;

import com.accenture.java.msopentdmaven.repository.common.BaseDownstreamResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoResponse extends BaseDownstreamResponse {

    private String productId;

    public ProductInfoResponse() {
    }

    public ProductInfoResponse(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
