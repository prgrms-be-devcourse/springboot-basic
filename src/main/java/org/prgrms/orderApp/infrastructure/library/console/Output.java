package org.prgrms.orderApp.infrastructure.library.console;

import org.prgrms.orderApp.domain.voucher.model.Voucher;

import java.util.List;

public interface Output {
    void voucherList(List<Voucher> vouchers);

    void infoMessage(String msg);

    void errorMessage(String msg);

    void print(String msg);



}
