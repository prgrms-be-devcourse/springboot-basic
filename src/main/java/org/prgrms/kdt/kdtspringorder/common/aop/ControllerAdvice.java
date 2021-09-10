package org.prgrms.kdt.kdtspringorder.common.aop;

import lombok.extern.log4j.Log4j2;
import org.prgrms.kdt.kdtspringorder.common.dto.ApiErrorResponse;
import org.prgrms.kdt.kdtspringorder.common.dto.ApiResponse;
import org.prgrms.kdt.kdtspringorder.common.enums.ErrorMessage;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.error("{}", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
            .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setErrorCode("METHOD_ARG_NOT_VALID");
        apiErrorResponse.setErrorMessage(errors);

        ApiResponse apiResponse = ApiResponse.builder()
            .error(apiErrorResponse)
            .status(HttpStatus.BAD_REQUEST)
            .success(false)
            .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
