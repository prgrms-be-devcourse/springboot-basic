package kr.co.springbootweeklymission.common.error.handler;

import kr.co.springbootweeklymission.common.response.ResponseError;
import kr.co.springbootweeklymission.common.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class NotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("======= Handle MethodArgumentNotValidException =======", e);
        final ResponseStatus responseStatus = ResponseStatus.FAIL_INVALID_PARAMETER;
        return handleExceptionInternal(e, responseStatus);
    }

    private ResponseEntity<ResponseError> handleExceptionInternal(final BindException e,
                                                                  final ResponseStatus responseStatus) {
        return ResponseEntity
                .status(responseStatus.getHttpStatus())
                .body(makeResponseErrorFormat(e, responseStatus));
    }

    private ResponseError makeResponseErrorFormat(final BindException e,
                                                  final ResponseStatus responseStatus) {
        final List<ResponseError.ValidationException> validationExceptions = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ResponseError.ValidationException::of)
                .toList();
        return ResponseError.builder()
                .message(responseStatus.getMessage())
                .httpStatus(responseStatus.getHttpStatus())
                .validationExceptions(validationExceptions)
                .build();
    }
}
