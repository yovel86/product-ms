package com.projects.product_ms.dtos;

import lombok.Data;

@Data
public class Response {
    private Object body;
    private String message;
    private ResponseStatus responseStatus;
}
