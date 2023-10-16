package com.prgrms.voucher_manage.util;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class OutputUtil {
    public void printMenu(){
        System.out.println("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type customer to get customer black list.
                """);
    }

    public void printVoucherSelect(){
        System.out.println("""
                === Voucher Program ===
                Type fixed to create fixed amount voucher.
                Type percent to create percent amount voucher.
                """);
    }

    public void requestDiscountPercentInfo(){
        System.out.println("\nType discount percent\n");
    }

    public void requestDiscountPriceInfo(){
        System.out.println("\nType discount price\n");
    }

    public void printFixedVoucherInfo(Voucher voucher){
        System.out.println(MessageFormat.format("\nVoucher id: {0} \nVoucher discount price: {1}\n"
                ,voucher.getVoucherId(), voucher.getDiscountAmount()));
    }

    public void printPercentVoucherInfo(Voucher voucher){
        System.out.println(MessageFormat.format("\nVoucher id: {0} \nVoucher discount percent: {1}%\n"
                ,voucher.getVoucherId(), voucher.getDiscountAmount()));
    }

    public void printCustomerInfo(Customer customer){
        System.out.println(MessageFormat.format("\nCustomer id: {0} \nCustomer name: {1}\n"
                ,customer.getCustomerId(), customer.getName()));
    }

    public void printMessage(String message){
        System.out.println(message+"\n");
    }
}
