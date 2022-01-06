package com.priceservice.Service;

import com.priceservice.Common.Header;
import com.priceservice.Dto.ProductPriceHTTPResponse;
import com.priceservice.Model.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    @Async
    public CompletableFuture<Product> getProductCompletableFuture(String product, String source) {
        ProductPriceHTTPResponse response = new ProductPriceHTTPResponse();
        RestTemplate restTemplate = new RestTemplate();
        Product productData = new Product();
        productData.setProductCode(product);
        productData.setCurrentTime(new Date());
        productData.setUserId(Header.getUserId());
        productData.setSource(source);
        long elapsedTime = 0;
        try {
            URI uri = new URI("https://price-" + source + ".free.beeceptor.com/service/" + product + "/price");
            long startTime = System.currentTimeMillis();
            response = restTemplate.getForObject(uri, ProductPriceHTTPResponse.class);
            elapsedTime = System.currentTimeMillis() - startTime;
            productData.setResponseTime(elapsedTime);
            productData.setPrice(response.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(productData);
    }
}
