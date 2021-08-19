package org.prgrms.kdt.io;

import org.prgrms.kdt.strategy.Voucher;

public class ConsoleOutput implements Output {
    private static final String VOUCHER_LIST_MESSAGE = "Voucher List";
    private static final String VOUCHER_MESSAGE = "Created";

    @Override
    public void printVoucherList() {

    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(VOUCHER_MESSAGE);
        System.out.println(voucher);
    }

    @Override
    public void newLine() {
        System.out.println();
    }
}
