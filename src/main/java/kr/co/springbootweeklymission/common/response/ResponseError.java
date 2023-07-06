package kr.co.springbootweeklymission.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public record ResponseError(String message,
                            HttpStatus httpStatus,
                            @JsonInclude(JsonInclude.Include.NON_EMPTY)
                            List<ValidationException> validationExceptions) {
    @Getter
    @Builder
    @RequiredArgsConstructor
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
