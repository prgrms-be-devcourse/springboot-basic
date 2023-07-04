package com.programmers.voucher.console;

import java.util.Map;

public interface Console {
    String getCondition();

    String getVoucherOperation();

    Integer getVoucherVersion();

    Integer getAmount();

    Integer getRate();

    String getVoucherId();

    String getCustomerOperation();

    Map<String, String> getCustomerInformation();

    String getCustomerId();

    String getCustomerName();
}
