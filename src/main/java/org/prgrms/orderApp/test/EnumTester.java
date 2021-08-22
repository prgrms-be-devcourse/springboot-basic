package org.prgrms.orderApp.test;

import org.prgrms.orderApp.model.voucher.VoucherType;
import org.springframework.util.Assert;

import java.text.MessageFormat;

public class EnumTester {
    public static void main(String[] args) {
        //voucherTypeTest();
        //scriptTest();
        createVoucherPageMenuTest();
    }
    static void voucherTypeTest(){
        System.out.println(VoucherType.FIXEDAMOUNT.getLimitAmount());
    }
    static void scriptTest(){
        System.out.println(Script.Exit_WarringMessage.getMessage());
        System.out.println(Script.InputUserSelectedMenuNumber_GuideMessage.getMessage());
    }
    static void createVoucherPageMenuTest() {
        Assert.isTrue(CreateVoucherPageMenu.getMenuName(1) == "FIXED", MessageFormat.format("MenuName({0}) is NOT MATCHED ", CreateVoucherPageMenu.FIXEDAMOUNT.getMenuName()));

    }
}
