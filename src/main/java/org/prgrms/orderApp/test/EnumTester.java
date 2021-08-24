package org.prgrms.orderApp.test;

import org.prgrms.orderApp.domain.voucher.model.VoucherType;

public class EnumTester {
    public static void main(String[] args) {
        //voucherTypeTest();
        //scriptTest();
        //createVoucherPageMenuTest();
    }
    static void voucherTypeTest(){
        System.out.println(VoucherType.FIXEDAMOUNT.getLimit());
    }
    static void scriptTest(){
        //System.out.println(Script.Exit_WarringMessage.getMessage());
        //System.out.println(Script.InputUserSelectedMenuNumber_GuideMessage.getMessage());
    }
    static void createVoucherPageMenuTest() {
        //Assert.isTrue(CreateVoucherPageMenu.getMenuName(1) == "FIXED", MessageFormat.format("MenuName({0}) is NOT MATCHED ", CreateVoucherPageMenu.FIXEDAMOUNT.getMenuName()));

    }
}
