package com.example.priceservice.Controller;

import com.example.priceservice.Common.APIResponse;
import com.example.priceservice.Common.Header;
import com.example.priceservice.Dao.PriceDAO;
import com.example.priceservice.Dto.PriceResponse;
import com.example.priceservice.Model.Price;
import com.example.priceservice.Service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
    @Autowired
    PriceService priceService;

    @GetMapping("app/{product}/price")
    public APIResponse getPrice(@RequestHeader("x-user") String user) {
        APIResponse response = null;
        Header.setUserId(user);

        // code that uses the language variable
        return response;
    }
}
