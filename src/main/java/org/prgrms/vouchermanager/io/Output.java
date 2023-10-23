package org.prgrms.vouchermanager.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class Output {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public void selectMenu(){
        System.out.println("Select menu\n" + "1. Voucher menu\n" + "2. Customer menu");
    }
    public void voucherInit(){
        System.out.println("=== Voucher Program ===\n" + "Type **exit** to exit the program.\n" + "Type **create** to create a new voucher.\n" + "Type **list** to list all vouchers.\n" + "```");
    }

    public void createVoucherMenu(){
        System.out.println("=== Create Voucher Menu ===\n" + "Select Voucher Mode(Fixed or Percent)");
    }

    public void customerInit(){
        System.out.println("=== Blacklsit Customer Program ===\n" + "Type **exit** to exit the program.\n" + "Type **create** to create a new customer.\n" + "Type **list** to list all customers.\n" + "```");
    }

    public void print(String message){
        System.out.println(message);
    }

    public void outputCustomerName(){
        System.out.println("블랙리스트에 등록 할 고객의 이름을 입력하세요.");
    }


}
