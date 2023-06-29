package org.promgrammers.voucher.view;

import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.VoucherType;
import org.promgrammers.voucher.domain.dto.VoucherRequestDto;

import java.util.Scanner;


@RequiredArgsConstructor
public class Console {
    private final Scanner scanner;

    public void printList(String[] messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

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

    public VoucherRequestDto createVoucherDto() {
        String type = askType();
        VoucherType voucherType = VoucherType.fromType(type);
        long amount = askDiscount();

        return new VoucherRequestDto(voucherType, amount);

    }

}
