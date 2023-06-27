package com.prgrms.spring.io;

import com.prgrms.spring.domain.menu.MenuType;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.Success;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class ConsoleView implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getMenu() {
        return scanner.nextLine();
    }

    @Override
    public int getVoucherType() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public Long getVoucherDiscount() {
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public void showMenu() {
        System.out.println("=== Voucher Program ===");
        for (MenuType mt : MenuType.values()) {
            System.out.println(String.format("Type **%s** to %s.", mt.getName(), mt.getExplain()));
        }
    }

    @Override
    public void showVoucherTypes() {
        System.out.println("생성하고 싶은 바우처 타입을 숫자로 선택해주세요.");
        for (VoucherType vt : VoucherType.values()) {
            System.out.println(String.format("%s. %s", vt.getId(), vt.getName()));
        }
    }

    @Override
    public void showVoucherPrompt(VoucherType voucherType) {
        System.out.println(voucherType.getPromptMessage());
    }

    @Override
    public void showAllVouchers(List<String> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void showErrorMsg(Error error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void showSuccessMsg(Success success) {
        System.out.println(success.getMessage());
    }
}
