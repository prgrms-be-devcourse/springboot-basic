package org.prgrms.springorder.global.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;

    private int statusCode;

    private String requestUri;

}
