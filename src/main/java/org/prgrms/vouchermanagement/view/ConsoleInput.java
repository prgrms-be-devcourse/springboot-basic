package org.prgrms.vouchermanagement.view;

import org.prgrms.vouchermanagement.dto.VoucherCreateInfo;
import org.prgrms.vouchermanagement.dto.VoucherUpdateInfo;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.exception.InvalidInputException;
import org.prgrms.vouchermanagement.voucher.PolicyStatus;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleOutput consoleOutput = new ConsoleOutput();

    public Command commandInput() {
        consoleOutput.getInput();
        String inputCommand = scanner.nextLine();
        return validateAndConvertToCommand(inputCommand);
    }

    public VoucherCreateInfo createVoucherInput() {
        consoleOutput.getInput();
        String inputPolicy = scanner.nextLine();
        PolicyStatus policy = validateAndConvertPolicy(inputPolicy);

        consoleOutput.getInput();
        String inputAmountOrPercent = scanner.nextLine();
        long amountOrPercent = validateAndConvertAmountOrPercent(inputAmountOrPercent);
        return new VoucherCreateInfo(policy, amountOrPercent);
    }

    public VoucherUpdateInfo updateVoucherInput() {
        consoleOutput.getInput();
        UUID voucherId = UUID.fromString(scanner.nextLine());

        consoleOutput.getInput();
        String inputAmountOrPercent = scanner.nextLine();
        long amountOrPercent = validateAndConvertAmountOrPercent(inputAmountOrPercent);
        return new VoucherUpdateInfo(voucherId, amountOrPercent);
    }

    public WalletCreateInfo createWalletInput() {
        consoleOutput.getInput();
        UUID customerId = UUID.fromString(scanner.nextLine());

        consoleOutput.getInput();
        UUID voucherId = UUID.fromString(scanner.nextLine());
        return new WalletCreateInfo(customerId, voucherId);
    }

    public UUID findAllVouchersByCustomerId() {
        consoleOutput.getInput();
        return UUID.fromString(scanner.nextLine());
    }

    public UUID deleteVoucherByCustomerId() {
        consoleOutput.getInput();
        return UUID.fromString(scanner.nextLine());
    }

    public UUID findCustomerByVoucherId() {
        consoleOutput.getInput();
        return UUID.fromString(scanner.nextLine());
    }

    private Command validateAndConvertToCommand(String inputCommand) {
        try {
            return Command.valueOf(inputCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("잘못된 command 입력입니다.");
        }
    }

    private PolicyStatus validateAndConvertPolicy(String inputPolicy) {
        try {
            return PolicyStatus.valueOf(inputPolicy.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("잘못된 policy 입력입니다.");
        }
    }

    private long validateAndConvertAmountOrPercent(String inputAmountOrPercent) {
        try {
            return Long.parseLong(inputAmountOrPercent);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("잘못된 amountOrPercent 입력입니다.");
        }
    }
}
