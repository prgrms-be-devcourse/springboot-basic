package kr.co.springbootweeklymission.common.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFormat<T> {
    private static final boolean SUCCESS = true;
    private static final boolean FAIL = false;

    private boolean isSuccessful;
    private Optional<T> data;
    private String message;
    private HttpStatus status;

    public static <T> ResponseFormat<T> success(ResponseStatus responseStatus) {
        return ResponseFormat.<T>builder()
                .isSuccessful(SUCCESS)
                .data(Optional.empty())
                .message(responseStatus.getMessage())
                .status(responseStatus.getHttpStatus())
                .build();
    }

    public static <T> ResponseFormat<T> successWithData(ResponseStatus responseStatus,
                                                        T data) {
        return ResponseFormat.<T>builder()
                .isSuccessful(SUCCESS)
                .data(Optional.ofNullable(data))
                .message(responseStatus.getMessage())
                .status(responseStatus.getHttpStatus())
                .build();
    }

    public static <T> ResponseFormat<T> fail(ResponseStatus responseStatus) {

        return ResponseFormat.<T>builder()
                .isSuccessful(FAIL)
                .data(Optional.empty())
                .message(responseStatus.getMessage())
                .status(responseStatus.getHttpStatus())
                .build();
    }

    public static <T> ResponseFormat<T> failWithData(ResponseStatus responseStatus,
                                                     T data) {

        return ResponseFormat.<T>builder()
                .isSuccessful(FAIL)
                .data(Optional.ofNullable(data))
                .message(responseStatus.getMessage())
                .status(responseStatus.getHttpStatus())
                .build();
    }
}
