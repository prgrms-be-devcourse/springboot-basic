package org.prgrms.orderApp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException{
    private final RestApiErrorCode errorCode;

}
