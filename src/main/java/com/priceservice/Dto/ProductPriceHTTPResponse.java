package com.priceservice.Dto;

import lombok.Data;

@Data
public class ProductPriceHTTPResponse {
    private Integer price;
    private String product;
}
