package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.Scanner;

public class temp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine();

        try{
            VoucherType voucherType = VoucherType.valueOf(temp);
        }catch(Exception e){
            throw e;
        }
    }


}
