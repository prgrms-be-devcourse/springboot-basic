package com.programmers.springbootbasic.domain.voucher;

public enum VoucherState {
    EXPIRE("기간만료"),
    AVAILABLE("사용가능"),
    USED("사용완료");

    private final String state;

    VoucherState(String state) {
        this.state = state;
    }
}
