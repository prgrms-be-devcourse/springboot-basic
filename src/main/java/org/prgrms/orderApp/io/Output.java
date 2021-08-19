package org.prgrms.orderApp.io;

import org.prgrms.orderApp.model.voucher.Voucher;

import java.util.List;

public interface Output {
    void voucherList(List<Voucher> vouchers);

    void infoMessage(String msg);

    void errorMessage(String msg);

    void print(String msg);



}
