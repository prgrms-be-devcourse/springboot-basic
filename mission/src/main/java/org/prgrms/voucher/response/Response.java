package org.prgrms.voucher.response;

public record Response(ResponseState state, Object data) {

    private static Response of(ResponseState state, Object data) {

        return new Response(state, data);
    }
}
