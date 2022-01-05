package com.example.priceservice.Dto;

import lombok.Data;

@Data
public class ProductPriceHTTPResponse {
    private Integer price;
    private String product;
    private long elapsedTime;
}
