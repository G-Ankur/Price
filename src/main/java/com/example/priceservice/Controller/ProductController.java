package com.example.priceservice.Controller;

import com.example.priceservice.Common.APIResponse;
import com.example.priceservice.Common.Header;
import com.example.priceservice.Dto.PriceResponse;
import com.example.priceservice.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductService priceService;

    @GetMapping("app/{product}/price")
    public PriceResponse getPrice(@PathVariable("product") String product, @RequestHeader(name = "X-User", required = false) String user) {
//        logger.info("UserId : " + user);
        PriceResponse priceResponse = null;
        Header.setUserId(user);
        priceResponse = priceService.getPrice(product);
        return priceResponse;
    }
}
