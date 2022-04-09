package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.voucher.VoucherType;

import java.util.List;

public class Console implements Input, Output {

    @Override
    public Menu inputMenu() {
        return null;
    }

    @Override
    public VoucherType inputVoucherType() {
        return null;
    }

    @Override
    public Double inputPercent() {
        return null;
    }

    @Override
    public Double inputAmount() {
        return null;
    }

    @Override
    public void printBlackList(List<String> blackList) {

    }

    @Override
    public void printPathError() {

    }

    @Override
    public void printMenuError() {

    }
}
