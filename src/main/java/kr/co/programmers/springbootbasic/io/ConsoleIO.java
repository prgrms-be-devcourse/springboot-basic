package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.io.enums.*;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConsoleIO implements Input, Output {
    private final Scanner scanner;

    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public EntireServiceCommand readEntireServiceCommand() {
        String input = scanner.nextLine();

        return EntireServiceCommand.resolve(input);
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
    public VoucherServiceCommand readVoucherCommand() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return VoucherServiceCommand.resolve(input);
    }

    @Override
    public CustomerServiceCommand readCustomerServiceCommand() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return CustomerServiceCommand.resolve(input);
    }

    @Override
    public String readCustomerName() {
        return scanner.nextLine();
    }

    @Override
    public CustomerFindCommand readCustomerFindCommand() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return CustomerFindCommand.resolve(input);
    }

    @Override
    public String readUUID() {
        return scanner.nextLine();
    }

    @Override
    public CustomerStatus readCustomerStatus() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return CustomerStatus.resolveId(input);
    }

    @Override
    public WalletServiceCommand readWalletServiceCommand() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return WalletServiceCommand.resolve(input);
    }

    @Override
    public void printProgramMenu() {
        System.out.print(ConsoleMessage.VOUCHER_PROGRAM_MENU);
    }

    @Override
    public void printVoucherCreateMenu() {
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
    public void printVoucherMessage(VoucherResponse dto) {
        String message = ApplicationUtils.formatVoucherResponseDto(dto);
        System.out.print(message);
    }

    @Override
    public void printCustomerMessage(CustomerResponse dto) {
        String message = ApplicationUtils.formatCustomerResponseDto(dto);
        System.out.print(message);
    }

    @Override
    public void printVoucherListMessage(List<VoucherResponse> list) {
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
    public void printCustomerListMessage(List<CustomerResponse> list) {
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

    @Override
    public void printCustomerFindMenu() {
        System.out.print(ConsoleMessage.CUSTOMER_FIND_MENU);
    }

    @Override
    public void printCustomerServiceMenu() {
        System.out.print(ConsoleMessage.CUSTOMER_SERVICE_MENU);
    }

    @Override
    public void printCustomerCreateMessage() {
        System.out.print(ConsoleMessage.CUSTOMER_NAME_MESSAGE);
    }

    @Override
    public void printWalletServiceMenu() {
        System.out.print(ConsoleMessage.WALLET_SERVICE_MENU);
    }

    @Override
    public void printNoResult() {
        System.out.print(ConsoleMessage.NO_RESULT_MESSAGE);
    }

    @Override
    public void printCustomerUuidTypeMessage() {
        System.out.print(ConsoleMessage.CUSTOMER_UUID_MESSAGE);
    }

    @Override
    public void printTypeCustomerStatus() {
        System.out.print(ConsoleMessage.CUSTOMER_STATUS_MESSAGE);
    }

    @Override
    public void printVoucherUuidTypeMessage() {
        System.out.print(ConsoleMessage.VOUCHER_UUID_MESSAGE);
    }

    @Override
    public void printWalletUuidTypeMessage() {
        System.out.print(ConsoleMessage.WALLET_UUID_MESSAGE);
    }

    @Override
    public void printWalletSaveMessage(WalletSaveDto responseDto) {
        String message = ApplicationUtils.formatWalletSaveDto(responseDto);
        System.out.print(message);
    }

    @Override
    public void printWalletFindMessage(WalletResponse walletResponse) {
        String message = ApplicationUtils.formatWalletFindResponse(walletResponse);
        System.out.print(message);
    }

    @Override
    public void printVoucherServiceMenu() {
        System.out.print(ConsoleMessage.VOUCHER_SERVICE_MENU);
    }

    @Override
    public void printCustomerDeleteMessage(String customerId) {
        String message = MessageFormat.format("유저 아이디 : {0}가 삭제됐습니다.\n", customerId);
        System.out.println(message);
    }
}
