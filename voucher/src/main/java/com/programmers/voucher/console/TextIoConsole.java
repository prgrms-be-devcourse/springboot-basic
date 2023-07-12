package com.programmers.voucher.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextIoConsole implements Console {
    private TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getCondition() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== WELCOME TO APPLICATION === \n" +
                        "Type voucher to manage the voucher \n" +
                        "Type customer to manage the customer \n" +
                        "Type black to see blacklist_customer\n" +
                        "Type exit to exit the program \n" +
                        "[enter type] : ");
        return stringType;
    }

    @Override
    public String getVoucherOperation() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== Voucher Program === \n" +
                        "Type create to create the voucher\n" +
                        "Type findById to find the exact one voucher\n" +
                        "Type findAll to find all vouchers\n" +
                        "Type update to update your voucher\n" +
                        "Type deleteAll to delete all vouchers\n" +
                        "Type deleteById to delete exact one voucher\n" +
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
    public Integer getAmountOrRate() {
        System.out.println();
        Integer amount = textIO.newIntInputReader()
                .read("Enter the amount or rate of discount\n" +
                        "<<caution>> rate of discount means percentage versus original price\n" +
                        "ex) 70 => discount price = original price * (70 / 100)\n" +
                        "[amount or rate of discount] : ");
        return amount;
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

    @Override
    public String getVoucherId() {
        System.out.println();
        String voucherId = textIO.newStringInputReader()
                .read("Type voucherId\n" +
                        "[voucher type] : ");
        return voucherId;
    }

    @Override
    public String getCustomerOperation() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== Customer Program === \n" +
                        "Type create to create the Customer\n" +
                        "Type findById to find the exact one customer\n" +
                        "Type findAll to find all customers\n" +
                        "Type update to update customer\n" +
                        "Type deleteAll" +
                        "Type deleteById to delete exact one customer" +
                        "[enter type] : ");
        return stringType;
    }

    @Override
    public Map<String, String> getCustomerInformation() {
        System.out.println();
        String name = textIO.newStringInputReader()
                .read("Enter the information of Customer\n" +
                        "[customer name] : ");
        String email = textIO.newStringInputReader()
                .read("[customer email] : ");
        return Map.of("name", name, "email", email);
    }

    @Override
    public String getCustomerId() {
        System.out.println();
        return textIO.newStringInputReader().read("Enter the Id of Customer\n" +
                "[customer Id] : ");
    }

    @Override
    public String getCustomerName() {
        System.out.println();
        return textIO.newStringInputReader().read("Enter the Name of Customer\n" +
                "[customer name] : ");
    }

}
