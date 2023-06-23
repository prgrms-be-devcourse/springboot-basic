package com.programmers.springweekly.view;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Output {
    void outputProgramGuide();
    void outputSelectCreateVoucherGuide();
    void outputDiscountGuide();
    void outputExitMessage();
    void outputGetVoucherAll(Map<UUID, Voucher> voucherMap);
    void outputGetCustomerBlackList(Map<UUID, Customer> customerMap);
    void outputErrorMessage(String errorText);
}
