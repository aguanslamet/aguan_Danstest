package com.jwtAuth.test.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultResponse<T> {
    private T message;
    private T statusCode;
    private T data;
}
