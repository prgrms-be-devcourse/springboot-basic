package com.prgrms.spring.io;

import com.prgrms.spring.controller.dto.request.CustomerCreateRequestDto;
import com.prgrms.spring.controller.dto.request.VoucherCreateRequestDto;
import com.prgrms.spring.domain.customer.Customer;
import com.prgrms.spring.domain.menu.MenuType;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.Success;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public void showAllVouchers(List<Voucher> vouchers) {
        List<String> outputList = new ArrayList<>();
        vouchers.forEach(voucher -> outputList.add(String.format("%s : %d %s", voucher.getVoucherName(), voucher.getDiscount(), voucher.getDiscountUnit())));
        outputList.forEach(System.out::println);
    }

    @Override
    public void showErrorMsg(Error error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void showSuccessMsg(Success success) {
        System.out.println(success.getMessage());
    }

    @Override
    public void showAllCustomers(List<Customer> customers) {
        List<String> outputList = new ArrayList<>();
        customers.forEach(customer -> outputList.add(String.format("Name : %s \nEmail : %s\n", customer.getName(), customer.getEmail())));
        outputList.forEach(System.out::println);
    }

    public CustomerCreateRequestDto getCustomerCreateRequestDto() {
        System.out.print("이름을 입력해주세요 : ");
        String name = scanner.nextLine();
        System.out.print("이메일을 입력해주세요 : ");
        String email = scanner.nextLine();
        return CustomerCreateRequestDto.of(name, email);
    }

    public VoucherCreateRequestDto getVoucherCreateRequestDto() {
        showVoucherTypes();
        VoucherType type = VoucherType.matchType(getVoucherType());
        showVoucherPrompt(type);
        Long discount = getVoucherDiscount();
        return VoucherCreateRequestDto.of(type, discount);
    }
}
