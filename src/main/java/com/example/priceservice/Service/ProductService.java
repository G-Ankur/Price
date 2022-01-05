package com.example.priceservice.Service;

import com.example.priceservice.Common.Header;
import com.example.priceservice.Common.ProjectConstant;
import com.example.priceservice.Dao.ProductDAO;
import com.example.priceservice.Dto.PriceResponse;
import com.example.priceservice.Dto.ProductPriceHTTPResponse;
import com.example.priceservice.Model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    ProductDAO productDAO;

    public PriceResponse getPrice(String product) {
        PriceResponse priceResponse = new PriceResponse();  //creating object of PriceResponse
        //Checking if the Product is already in db or not
//        Product productExist = productDAO.findByUserIdAndProductCode(Header.getUserId(), product);
//        if (productExist != null) {
//            //if the record exist then return the price of it
//            priceResponse.setPrice(productExist.getPrice());
//            return priceResponse;
//        }

        //Setting Value of Product in Model for DB Insertion using DAO
        Product productData = new Product();
        productData.setProductCode(product);
        productData.setCurrentTime(new Date());
        productData.setUserId(Header.getUserId());
        ProductPriceHTTPResponse amazonPriceResponse = getSourcePrice(product, ProjectConstant.AMAZON);
//        logger.info("Amazon Price : " + amazonPriceResponse.getPrice() + " Elapsed Time : "+ amazonPriceResponse.getElapsedTime());
        ProductPriceHTTPResponse flipkartPriceResponse = getSourcePrice(product, ProjectConstant.FLIPKART);
//        logger.info("Flipkart Price : " + flipkartPriceResponse.getPrice() + " Elapsed Time : " + flipkartPriceResponse.getElapsedTime());
        //Comparing Source Price and taking lesser Price Source in DB
        if (amazonPriceResponse.getElapsedTime() > flipkartPriceResponse.getElapsedTime()) {
            productData.setResponseTime(flipkartPriceResponse.getElapsedTime());
            productData.setSource(ProjectConstant.FLIPKART);
            productData.setPrice(flipkartPriceResponse.getPrice());
            productDAO.saveAndFlush(productData);                                   //Saving Data in DB
            priceResponse.setPrice(flipkartPriceResponse.getPrice());              // Setting less price in Response
        } else {
            productData.setResponseTime(amazonPriceResponse.getElapsedTime());
            productData.setSource(ProjectConstant.AMAZON);
            productData.setPrice(amazonPriceResponse.getPrice());
            productDAO.saveAndFlush(productData);                                   //Saving Data in DB
            priceResponse.setPrice(amazonPriceResponse.getPrice());                // Setting less price in Response
        }
//        logger.info("response : " + priceResponse.getPrice());
        return priceResponse;
    }

    public ProductPriceHTTPResponse getSourcePrice(String product, String source) {
        ProductPriceHTTPResponse entity = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = "https://price-" + source + ".free.beeceptor.com/service/" + product + "/price";
//            logger.info("URL : " + url);
            URI uri = new URI(url);
            long startTime = System.currentTimeMillis();
            entity = restTemplate.getForObject(uri, ProductPriceHTTPResponse.class);
            long elapsedTime = System.currentTimeMillis() - startTime;
//            logger.info("Source : " + source);
//            logger.info("StartTime : " + startTime);
//            logger.info("elapsedTime : " + elapsedTime);
            entity.setElapsedTime(elapsedTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
