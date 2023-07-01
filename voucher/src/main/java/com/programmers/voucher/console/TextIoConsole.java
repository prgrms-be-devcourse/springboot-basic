package com.programmers.voucher.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class TextIoConsole implements Console {
    private TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getCondition() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== Voucher Program === \n" +
                        "Type exit to exit the program \n" +
                        "Type create to create a new voucher. \n" +
                        "Type list to list all vouchers. \n" +
                        "Type black to see blacklist_customer.\n" +
                        "[enter type] : ");
        return stringType;
    }

    @Override
    public Integer getVoucherVersion() {
        System.out.println();
        Integer voucherVersion = textIO.newIntInputReader()
                .read("Choose version of Voucher\n" +
                                "Fixed AmountVoucher     : 1\n" +
                                "PercentDiscountVoucher  : 2\n" +
                                "[enter number of version]");
        return voucherVersion;
    }

    @Override
    public Integer getAmount() {
        System.out.println();
        Integer amount = textIO.newIntInputReader()
                .read("Enter the amount of discount\n" +
                        "[amount of discount] : ");
        return amount;
    }

    @Override
    public Integer getRate() {
        System.out.println();
        Integer rate = textIO.newIntInputReader()
                .read("Enter the rate of discount\n" +
                        "<<caution>> rate of discount means percentage versus original price\n" +
                        "ex) 70 => discount price = original price * (70 / 100)\n" +
                        "[rate of discount] : ");
        return rate;
    }
}
