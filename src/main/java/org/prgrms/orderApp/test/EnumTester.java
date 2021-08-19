package org.prgrms.orderApp.test;

import org.prgrms.orderApp.model.voucher.VoucherType;
import org.prgrms.orderApp.io.Script;

public class EnumTester {
    public static void main(String[] args) {
        voucherTypeTest();
        scriptTest();
    }
    static void voucherTypeTest(){
        System.out.println(VoucherType.FIXEDAMOUNT.getLimitAmount());
    }
    static void scriptTest(){
        System.out.println(Script.Exit_WarringMessage.getMessage());
        System.out.println(Script.InputUserSelectedMenuNumber_GuideMessage.getMessage());
    }
}
