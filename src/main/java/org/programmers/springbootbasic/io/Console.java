package org.programmers.springbootbasic.io;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.Customer;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.dto.VoucherInputDto;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Component
public class Console implements Input, Output {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static final String VOUCHER_MAIN_MENU = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all voucher.\n" +
            "Type blacklist to list all blacklist.";

    private static final String TYPE_USER_COMMAND = "type your command -> ";
    private static final String TYPE_VOUCHER_INFO = "type voucher's information(type, amount)";
    private static final String TYPE_VOUCHER_INFO_TYPE = "type -> ";
    private static final String TYPE_VOUCHER_INFO_AMOUNT = "amount -> ";
    private static final String WRONG_INPUT = "잘못된 입력입니다.";



    @Override
    public VoucherMainMenuCommand getVoucherMainMenuInput() throws IOException {
        System.out.print(TYPE_USER_COMMAND);
        return VoucherMainMenuCommand.valueOfCommand(br.readLine());
    }

    @Override
    public VoucherInputDto getVoucherCreateMenuInput() throws IOException {
        // type, amount 입력
        System.out.println(TYPE_VOUCHER_INFO);
        System.out.print(TYPE_VOUCHER_INFO_TYPE);
        String type = br.readLine().strip();
        System.out.print(TYPE_VOUCHER_INFO_AMOUNT);
        long amount = Long.parseLong(br.readLine());
        return new VoucherInputDto(type, amount);
    }

    @Override
    public void printMainMenu() {
        System.out.println(VOUCHER_MAIN_MENU);
    }

    @Override
    public void printError() {
        System.out.println(WRONG_INPUT);
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printBlacklist(List<Customer> blacklist) {
        blacklist.forEach(System.out::println);
    }
}
