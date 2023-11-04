package org.prgrms.vouchermanager.handler;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.common.ApiResponse;
import org.prgrms.vouchermanager.dto.ErrorDto;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> errorHandler(RuntimeException e){
        log.error(e.getMessage());
        return new ResponseEntity<ErrorDto>(new ErrorDto("error"), HttpStatus.NOT_FOUND);
    }
}
