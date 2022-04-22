package org.prgrms.voucher.response;

public record Response(
        ResponseState responseState,
        Object responseData) {

    private static Response of(
            ResponseState responseState,
            Object responseData) {

        return new Response(responseState, responseData);
    }
}
