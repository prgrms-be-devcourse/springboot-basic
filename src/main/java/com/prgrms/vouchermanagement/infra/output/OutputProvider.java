package com.prgrms.vouchermanagement.infra.output;

import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;

public interface OutputProvider {

    void printMessage(String message);
    void printVoucherTypeMessage(String message);
    void printMessage(VouchersResponse vouchersResponse);

}
