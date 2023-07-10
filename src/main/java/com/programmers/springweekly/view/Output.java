package com.programmers.springweekly.view;

import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;

public interface Output {

    void outputProgramGuide();

    void outputSelectCreateVoucherGuide();

    void outputDiscountGuide();

    void outputExitMessage();

    void outputGetVoucherAll(VoucherListResponse voucherListResponse);


    void outputVoucherUpdateGuide();

    void outputGetCustomerList(CustomerListResponse customerList);

    void outputErrorMessage(String errorText);

    void outputUUIDGuide();

    void outputCustomerUpdateGuide();

    void outputCustomerCreateGuide();

    void outputCompleteGuide();

    void outputCustomerMenuGuide();

    void outputVoucherMenuGuide();
    
}
