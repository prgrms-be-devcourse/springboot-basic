package com.programmers.part1.error.voucher;

// PercentVoucher에서 amount가 0이하 100초과 일 경우 처리
public class PercentErrorException extends RuntimeException {

    public PercentErrorException(String message) {
        super(message);
    }

}
