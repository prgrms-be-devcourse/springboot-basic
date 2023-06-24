package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.Collection;

public interface OutputView {

    void showMenu();

    void showQuitMessage();

    void showVoucherTypes();

    void showFixDiscountPriceInputMessage();

    void showPercentDiscountRateInputMessage();

    void showVoucherCreationSuccessMessage(String voucherInfo);

    void showAllVouchers(Collection<Voucher> vouchers);

    void printWithLineBreak();

    void printWithLineBreak(String data);

    void printWithoutLineBreak(String data);
}
