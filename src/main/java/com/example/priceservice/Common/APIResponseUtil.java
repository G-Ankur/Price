package com.example.priceservice.Common;

import org.springframework.stereotype.Component;

@Component
public class APIResponseUtil {
    public APIResponse successResponse(Object data) {
        APIResponse response = new APIResponse();
        response.setData(data);
        response.setMessage(ProjectConstant.SUCCESS_MESSAGE);
        response.setStatus(ProjectConstant.SUCCESS_CODE);
        return response;
    }

    public APIResponse errorResponse(Object data, Exception e) {
        APIResponse response = new APIResponse();
        response.setData(data);
        response.setMessage(e.getMessage());
        response.setStatus(ProjectConstant.ERROR_CODE);
        return response;
    }
}
