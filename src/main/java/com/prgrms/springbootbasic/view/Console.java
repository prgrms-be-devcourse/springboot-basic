package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output {

    private static final Scanner input = new Scanner(System.in);

    public void consoleMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }

    @Override
    public String inputCommand(String command) {
        System.out.println("명령어를 입력해주세요.(exit,create,list)");
        return input.nextLine();
    }

    @Override
    public String inputVoucherType(String voucherType) {
        System.out.println("생성할 voucher의 종류를 입력해주세요.(FixedAmountVoucher, PercentDiscountVoucher)");
        return input.nextLine();
    }

    @Override
    public long inputVoucherDiscount(long voucherDiscount) {
        System.out.println("생성할 voucher의 금액을 입력해주세요!");
        return input.nextInt();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printlnVoucherList(Map<UUID, Voucher> voucherMap) {
        if (voucherMap.isEmpty()) {
            System.out.println("생성된 voucher가 없습니다.");
        } else {
            System.out.println("생성된 Voucher의 목록은 다음과 같습니다.");
            for (Voucher voucher : voucherMap.values()) {
                System.out.println("Voucher Type: " + voucher.getVoucherType() + "Discount" + voucher.getDiscount());
            }
        }
    }
}
