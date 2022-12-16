package prgms.vouchermanagementapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException
    ) {
        log.error("IllegalArgumentException: " + illegalArgumentException.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(illegalArgumentException.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException
    ) {
        log.error("HttpMediaTypeNotAcceptableException: " + httpMediaTypeNotAcceptableException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(httpMediaTypeNotAcceptableException.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        log.error("Internal Server Error: " + exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(exception.getMessage()));
    }
}
