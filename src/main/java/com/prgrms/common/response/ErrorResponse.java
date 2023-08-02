package com.prgrms.common.response;
import com.prgrms.common.codes.HttpErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;
    private String divisionCode;
    private String resultMsg;
    private String reason;

    protected ErrorResponse(final HttpErrorCode code, final String reason) {
        this.resultMsg = code.getMessage();
        this.status = code.getStatus();
        this.divisionCode = code.getDivisionCode();
        this.reason = reason;
    }

    public static ErrorResponse of(final HttpErrorCode code, final String reason) {
        return new ErrorResponse(code, reason);
    }

}
