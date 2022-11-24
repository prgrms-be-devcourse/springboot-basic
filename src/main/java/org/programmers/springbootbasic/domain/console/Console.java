package org.programmers.springbootbasic.domain.console;

import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.customer.dto.CustomerOutputDto;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.InputException;
import org.programmers.springbootbasic.exception.NotAnIntegerException;
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
    private static final String BUFFER_INPUT_ERROR = "입력 버퍼 문제로 입력 받을 수 없습니다.";
    private static final String NOT_AN_INTEGER = "Amount 입력 값은 정수여야 합니다.";


    @Override
    public VoucherMainMenuCommand getVoucherMainMenuInput() {
        System.out.print(TYPE_USER_COMMAND);
        String commandInput;
        try {
            commandInput = br.readLine();
            return VoucherMainMenuCommand.valueOfCommand(commandInput);
        } catch (IOException e) {
            throw new InputException(BUFFER_INPUT_ERROR);
        }
    }

    @Override
    public VoucherInputDto getVoucherCreateMenuInput() {
        // type, amount 입력
        String type;
        long amount;
        try {
            System.out.println(TYPE_VOUCHER_INFO);
            System.out.print(TYPE_VOUCHER_INFO_TYPE);
            type = br.readLine().strip();
            System.out.print(TYPE_VOUCHER_INFO_AMOUNT);
            amount = Long.parseLong(br.readLine());
            return new VoucherInputDto(type, amount);
        } catch (IOException e) {
            throw new InputException(BUFFER_INPUT_ERROR);
        } catch (NumberFormatException e) {
            throw new NotAnIntegerException(NOT_AN_INTEGER);
        }
    }

    @Override
    public void printMainMenu() {
        System.out.println(VOUCHER_MAIN_MENU);
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printBlacklist(List<CustomerOutputDto> blacklist) {
        blacklist.forEach(System.out::println);
    }
}
