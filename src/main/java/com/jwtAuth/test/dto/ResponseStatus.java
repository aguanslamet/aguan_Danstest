package com.jwtAuth.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStatus {
    private String responeCode;
    private String responseMessage;
}
