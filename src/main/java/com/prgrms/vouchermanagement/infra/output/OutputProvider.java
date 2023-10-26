package com.prgrms.vouchermanagement.infra.output;

import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public interface OutputProvider {

    void printMessage(String message);
    void printMessage(VoucherType voucherType);
    void printMessage(VouchersResponse vouchersResponse);

}
