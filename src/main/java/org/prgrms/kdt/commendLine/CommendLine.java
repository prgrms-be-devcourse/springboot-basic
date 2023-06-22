package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.voucher.Voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommendLine implements Input, Output{

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public void printMenu() {
        System.out.println("Type exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.");
    }

    @Override
    public void printVoucherTypes() {
        System.out.println("1. FixedAmountVoucher\n2. PercentDiscountVoucher");
    }

    @Override
    public void printAllBoucher(List<Voucher> vouchers) {

    }

    @Override
    public String getUserMenu() throws IOException {
        return br.readLine();
    }

    @Override
    public String getVoucherTypes() throws IOException {
        return br.readLine();
    }
}
