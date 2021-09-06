package org.prgrms.dev.io;

import org.prgrms.dev.customer.service.CustomerService;
import org.prgrms.dev.voucher.service.VoucherService;

public interface Output {
    void init();

    void voucherSelectType();

    void voucherList(VoucherService voucherService);

    void invalidNumberInput();

    void invalidCommandTypeInput();

    void invalidVoucherTypeInput();

    void blackList(CustomerService customerService);
}
