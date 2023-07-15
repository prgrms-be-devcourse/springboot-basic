package org.promgrammers.voucher.view;

import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.VoucherType;
import org.promgrammers.voucher.domain.dto.voucher.VoucherCreateRequestDto;

import java.util.Scanner;
import java.util.UUID;


@RequiredArgsConstructor

public class Console {
    private final Scanner scanner;


    public void print(String message) {
        System.out.println(message);
    }

    public String input() {
        return scanner.nextLine();
    }

    private String askType() {
        System.out.println("Voucher 타입을 고르시오 : fixed , percent");
        return input();
    }

    private long askDiscount() {
        System.out.println("할인 금액을 입력하시오.");
        return Long.parseLong(input());
    }

    public String askCustomerAction() {
        System.out.println("=== Service Program ===");
        System.out.println("Type 'create' to Create a customer");
        System.out.println("Type 'assign' to Delete a customer");
        System.out.println("Type 'list' to View all customers");
        System.out.println("Type 'findOne' to Find customers by type");
        System.out.println("Type 'update' to Find customers by type");
        System.out.println("Type 'delete' to Delete a customer");
        System.out.println("Type 'exit' to Find customers by type");

        return input();
    }

    public String askRemoveCustomerFindType() {
        System.out.println("=== Please select a service to use. ===");
        System.out.println("Type 'id' to remove a customer");
        System.out.println("Type 'all' to remove all customer");
        return input();
    }


    public VoucherCreateRequestDto createVoucherDto() {
        String type = askType();
        VoucherType voucherType = VoucherType.fromType(type);
        long amount = askDiscount();

        return new VoucherCreateRequestDto(UUID.randomUUID(), amount, voucherType);
    }

    public void display() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
    }


    public String askForCustomerFindType() {
        System.out.println("=== Please select the customer search type. ===");
        System.out.println("Type 'id' to Customer ID");
        System.out.println("Type 'name' to Customer name");
        System.out.println("Type 'voucherId' to Voucher ID owned by the customer");
        return input();
    }
}
