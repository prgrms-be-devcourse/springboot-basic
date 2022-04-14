package com.programmers.part1.exception.voucher;

/**
 * 고정 바우처일때 금액이 0원 이하 처리
 * */
public class FixedAmountException extends RuntimeException{
    public FixedAmountException(String message) {
        super(message);
    }
}
