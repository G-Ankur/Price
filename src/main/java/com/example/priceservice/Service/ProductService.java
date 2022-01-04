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
        Product productExist = productDAO.findByUserIdAndProductCode(Header.getUserId(), product);
        if (productExist != null) {
            //if the record exist then return the price of it
            priceResponse.setPrice(productExist.getPrice());
            return priceResponse;
        }

        //Setting Value of Product in Model for DB Insertion using DAO
        Product productData = new Product();
        productData.setProductCode(product);
        productData.setCurrentTime(new Date());
        productData.setUserId(Header.getUserId());
        Integer amazonPrice = getSourcePrice(product, ProjectConstant.AMAZON);
        logger.info("Amazon Price : " + amazonPrice);
        Integer flipkartPrice = getSourcePrice(product, ProjectConstant.FLIPKART);
        logger.info("Flipkart Price : " + flipkartPrice);
        //Comparing Source Price and taking lesser Price Source in DB
        if (amazonPrice > flipkartPrice) {
            productData.setResponseTime(new Date());
            productData.setSource(ProjectConstant.FLIPKART);
            productData.setPrice(flipkartPrice);
            productDAO.saveAndFlush(productData);               //Saving Data in DB
            priceResponse.setPrice(flipkartPrice);              // Setting less price in Response
        } else {
            productData.setResponseTime(new Date());
            productData.setSource(ProjectConstant.AMAZON);
            productData.setPrice(amazonPrice);
            productDAO.saveAndFlush(productData);               //Saving Data in DB
            priceResponse.setPrice(amazonPrice);                // Setting less price in Response
        }
        logger.info("response : " + priceResponse.getPrice());
        return priceResponse;
    }

    public Integer getSourcePrice(String product, String source) {
        ProductPriceHTTPResponse entity = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = "https://price-" + source + ".free.beeceptor.com/service/" + product + "/price";
            logger.info("URL : " + url);
            URI uri = new URI(url);
            entity = restTemplate.getForObject(uri, ProductPriceHTTPResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity.getPrice();
    }

}
