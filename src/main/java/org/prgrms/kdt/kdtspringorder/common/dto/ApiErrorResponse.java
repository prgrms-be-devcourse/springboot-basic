package org.prgrms.kdt.kdtspringorder.common.dto;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private Object errorMessage;
    // Error Code
    private String errorCode;

}
