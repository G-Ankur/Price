package com.example.priceservice.Common;

import org.springframework.stereotype.Component;

@Component
public class Header {
    private static String userId;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Header.userId = userId;
    }
}
