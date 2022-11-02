package com.programmers.springvoucherservice.view;

import com.programmers.springvoucherservice.domain.voucher.Voucher;

import java.util.List;

public interface View {
    void showMenu();

    void showVoucherLists(List<Voucher> vouchers);

    String getUserCommand();
}
