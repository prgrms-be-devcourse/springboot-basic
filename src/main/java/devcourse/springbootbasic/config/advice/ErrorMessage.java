package devcourse.springbootbasic.config.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
final class ErrorMessage {
    private final String error;
    private final String message;
}
