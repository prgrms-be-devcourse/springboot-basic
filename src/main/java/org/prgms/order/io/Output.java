package org.prgms.order.io;

import org.prgms.order.voucher.service.VoucherService;

public interface Output {

    void mainMenu();

    void selectVoucher();

    void voucherList(VoucherService voucherService);

}
