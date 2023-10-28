package com.programmers.springbootbasic.common.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandOutput {
    PROGRAM_SELECT("""
            === Program Select ===
            Type exit to exit the program.
            Type voucher to select Voucher program.
            Type customer to select Customer program.
            """),
    VOUCHER_SELECT("""
            === Select Voucher ===
            Type 1 to select Fixed Amount Voucher.
            Type 2 to select Percent Discount Voucher.
            """),
    VOUCHER_MENU("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type find to find voucher by voucher ID.
            Type update to update voucher value.
            Type delete to delete voucher.
            Type deleteAll to delete all vouchers.
            Type customer to show customers list which voucher have.
            """),
    CUSTOMER_MENU("""
            === Customer Program ===
            Type exit to exit the program.
            Type create to create customer.
            Type list to list all customers.
            Type blacklist to show all blacklist customers.
            Type addBlacklist to add customer in blacklist.
            Type removeBlacklist to remove customer from blacklist.
            Type deleteAll to delete all customers.
            Type addVoucher to add voucher into customer's wallet.
            Type removeVoucher to remove voucher from customer's wallet.
            Type wallet to show customer's wallet.
            """),
    REQUEST_VOUCHER_VALUE("Enter Value of Voucher "),
    REQUEST_VOUCHER_ID("Enter Voucher ID "),
    REQUEST_EMAIL("Enter Email "),
    REQUEST_NAME("Enter name "),
    EXIT("종료합니다."),
    WRONG_CHOICE("잘못된 메뉴 선택입니다.");

    private final String value;

}
