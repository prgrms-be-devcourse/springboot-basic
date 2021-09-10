package org.prgrms.kdt.kdtspringorder.common.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse {

    private boolean success;
    // HttpStatus
    private HttpStatus status;

    private ApiErrorResponse error;

    private Object payload;

}
