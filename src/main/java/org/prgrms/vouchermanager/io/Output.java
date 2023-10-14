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
        System.out.println("Type exit to exit the program.\n" + "Type list to list all vouchers.");
    }

    public void printVoucherInfo(String voucherInfo){
        System.out.println(voucherInfo);
    }

    public void print(String message){
        System.out.println(message);
    }


}
