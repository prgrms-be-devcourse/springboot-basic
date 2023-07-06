package com.programmers.springweekly.view;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;

import java.util.List;

public interface Output {

    void outputProgramGuide();

    void outputSelectCreateVoucherGuide();

    void outputDiscountGuide();

    void outputExitMessage();

    void outputGetVoucherAll(List<Voucher> voucherMap);

    void outputGetCustomerList(CustomerListResponse customerList);

    void outputErrorMessage(String errorText);

    void outputUUIDGuide();

    void outputCustomerUpdateGuide();

    void outputCustomerCreateGuide();

    void outputCompleteGuide();

    void outputCustomerMenuGuide();

    void outputVoucherMenu();

}
