package com.priceservice.Controller;

import com.priceservice.Common.Header;
import com.priceservice.Common.ProjectConstant;
import com.priceservice.Dao.ProductDAO;
import com.priceservice.Dto.PriceResponse;
import com.priceservice.Model.Product;
import com.priceservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {
    @Autowired
    ProductService priceService;
    @Autowired
    ProductDAO productDAO;

    @GetMapping("app/{product}/price")
    public PriceResponse FastPrice(@PathVariable("product") String product, @RequestHeader(name = "X-User", required = false) String user) throws ExecutionException, InterruptedException {
        Header.setUserId(user);
        PriceResponse response = new PriceResponse();
        Product productResponse = new Product();
        CompletableFuture future = CompletableFuture.anyOf(priceService.getProductCompletableFuture(product, ProjectConstant.AMAZON), priceService.getProductCompletableFuture(product, ProjectConstant.FLIPKART));
        future.complete(productResponse = (Product) future.get());
        response.setPrice(productResponse.getPrice());
        productDAO.save(productResponse);
        return response;
    }
}
