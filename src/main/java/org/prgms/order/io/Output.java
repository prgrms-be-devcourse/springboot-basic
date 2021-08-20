package org.prgms.order.io;

import org.prgms.order.voucher.repository.VoucherRepository;

import java.util.Optional;

public interface Output {

    void mainMenu();

    void selectVoucher();

    void voucherList(VoucherRepository voucherRepository);

}
