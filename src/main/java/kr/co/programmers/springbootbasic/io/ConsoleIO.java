package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.dto.CustomerResponseDto;
import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsoleIO implements Input, Output {
    private final Scanner scanner;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public MenuCommand readMenuCommand() {
        String input = scanner.nextLine();

        return MenuCommand.resolve(input);
    }

    @Override
    public VoucherType readVoucherType() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return VoucherType.resolveCommand(input);
    }

    @Override
    public long readAmount() {
        long input = scanner.nextLong();
        scanner.nextLine();

        return input;
    }

    @Override
    public void printProgramMenu() {
        System.out.print(ConsoleMessage.VOUCHER_PROGRAM_MENU);
    }

    @Override
    public void printCreationMenu() {
        System.out.print(ConsoleMessage.VOUCHER_CREATION_MENU);
    }

    @Override
    public void printAmountEnterMessage(VoucherType voucherType) {
        switch (voucherType) {
            case PERCENT_AMOUNT -> System.out.print(ConsoleMessage.PERCENT_AMOUNT_ENTER_MESSAGE);
            case FIXED_AMOUNT -> System.out.print(ConsoleMessage.FIXED_AMOUNT_ENTER_MESSAGE);
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.print(message);
    }

    @Override
    public void printVoucherMessage(VoucherResponseDto dto) {
        String message = ApplicationUtils.formatVoucherResponseDto(dto);
        System.out.print(message);
    }

    @Override
    public void printVoucherListMessage(List<VoucherResponseDto> list) {
        String message = list.stream()
                .map(ApplicationUtils::formatVoucherResponseDto)
                .collect(Collectors.joining());
        if (message.isEmpty()) {
            System.out.println(ConsoleMessage.EMPTY_LIST_MESSAGE);
        } else {
            System.out.print(message);
        }
    }

    @Override
    public void printCustomerListMessage(List<CustomerResponseDto> list) {
        String message = list.stream()
                .map(ApplicationUtils::formatCustomerResponseDto)
                .collect(Collectors.joining());
        if (message.isEmpty()) {
            System.out.println(ConsoleMessage.EMPTY_LIST_MESSAGE);
        } else {
            System.out.println(message);
        }
    }

    @Override
    public void printExit() {
        System.out.print(ConsoleMessage.EXIT_MESSAGE);
    }
}
