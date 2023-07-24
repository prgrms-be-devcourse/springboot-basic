package kr.co.springbootweeklymission.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
public record ResponseError(String exception,
                            String message,
                            HttpStatus httpStatus,
                            @JsonInclude(JsonInclude.Include.NON_EMPTY)
                            List<ValidationException> validationExceptions) {
    @Builder
    public record ValidationException(String message,
                                      String field) {
        public static ValidationException of(final FieldError fieldError) {
            return ValidationException.builder()
                    .message(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .build();
        }
    }
}
