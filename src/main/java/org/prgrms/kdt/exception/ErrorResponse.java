package org.prgrms.kdt.exception;

/**
 * Created by yhh1056
 * Date: 2021/09/07 Time: 9:28 오후
 */
public record ErrorResponse(
        String errorCode,
        String message
) {

}
