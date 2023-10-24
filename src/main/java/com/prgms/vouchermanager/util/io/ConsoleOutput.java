package com.prgms.vouchermanager.util.io;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput {


    public ConsoleOutput() {
    }
    public void printFrontMenu() {
        System.out.println("""
                
                사용할 메뉴를 선택해주세요.
                
                1. Voucher
                
                2. Customer
                
                3. EXIT
                """);
    }

    public void printVoucherMenu() {
        System.out.println("""
                === Voucher Program ===
                
                이용할 바우처 메뉴를 선택해주세요
                
                1. create
                
                2. list
                
                """);
    }


    public void printVoucherType() {
        System.out.println("""
                                
                이용하실 쿠폰의 타입 번호를 입력해주세요
                                
                1. FixedAmount
                
                2. PercentDiscount
                                
                """);
    }

    public void printCustomerMenu() {
        System.out.println("""
                
                이용하실 고객 메뉴를 입력해주세요
                
                1. black list
                
                """);
    }


    public void printVoucherAmount() {
        System.out.println("쿠폰의 할인값을 입력해주세요");
    }
}
