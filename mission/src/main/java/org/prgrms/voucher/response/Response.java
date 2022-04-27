package org.prgrms.voucher.response;

public record Response<T>(ResponseState state, T data) {

}
