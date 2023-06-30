package com.prgrms.springbootbasic.view;

public interface Input {

    String inputCommand(String message);

    String inputVoucherType(String voucherType);

    long inputVoucherDiscount(long discount);
}
