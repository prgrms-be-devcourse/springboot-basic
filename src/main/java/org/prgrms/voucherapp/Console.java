package org.prgrms.voucherapp;

import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.enums.*;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

/*
 * Console : 입출력을 관리하는 클래스
 * Q. discountAmount에 대한 예외처리를 discountAmount를 입력받았을 때 하는게 좋은지, discountAmount를 가지고 voucher를 생성하려고 할때 하는게 좋은지?
 * 현재 코드는 전자인데, 다른 input 메소드들은 input 받은 값들에 대한 책임을 지고 예외처리를 하고 있어서 discountAmountInput메소드가 amount의 유효성에 대한 책임까지 지는게 일관성있다고 생각했음.
 * 위 생각이 괜찮은 논리인건지 궁금합니다.
 * */
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public ModuleCommand moduleInput(String prompt) {
        System.out.println(prompt);
        return ModuleCommand
                .getMenu(scanner.nextLine())
                .orElseThrow(() -> (new WrongInputException("존재하지 않는 모듈을 입력하였습니다.")));
    }

    @Override
    public CrudCommand crudCommandInput(String prompt) {
        System.out.println(prompt);
        return CrudCommand
                .getMenu(scanner.nextLine())
                .orElseThrow(() -> (new WrongInputException("존재하지 않는 명령어를 입력하였습니다.")));
    }

    @Override
    public WalletCommand walletCommandInput(String prompt) {
        System.out.println(prompt);
        try{
            return WalletCommand
                    .getMenu(scanner.nextInt())
                    .orElseThrow(() -> (new WrongInputException("존재하지 않는 명령어를 선택하였습니다.")));
        }catch(InputMismatchException e){
            throw new WrongInputException("숫자를 입력해주세요.");
        } finally {
            scanner.nextLine();
        }
    }

    @Override
    public VoucherType voucherTypeInput(String prompt) {
        System.out.println(prompt);
        try {
            return VoucherType
                    .getType(scanner.nextInt())
                    .orElseThrow(() -> (new WrongInputException("존재하지 않는 바우처 타입을 입력하였습니다.")));
        } catch (InputMismatchException e) {
            throw new WrongInputException("숫자를 입력해주세요.");
        } finally {
            scanner.nextLine();
        }
    }

    @Override
    public long discountAmountInput(VoucherType voucherType, String prompt) {
        System.out.println(prompt);
        try {
            long discountAmount = scanner.nextLong();
            if (discountAmount <= 0 || discountAmount > voucherType.getMaxDiscountAmount())
                throw new WrongAmountException("잘못된 할인 금액을 입력하였습니다.");
            return discountAmount;
        } catch (InputMismatchException e) {
            throw new WrongInputException("양식에 맞지 않는 할인 금액을 입력하였습니다. 정수를 입력해주세요.");
        } finally {
            scanner.nextLine(); //버퍼비우기
        }
    }

    @Override
    public UUID UUIDInput(String prompt) {
        System.out.println(prompt);
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            throw new WrongInputException("올바르지 않은 UUID 양식입니다.");
        }
    }

    @Override
    public String customerNameInput(String prompt) {
        System.out.println(prompt);
        String customerName = scanner.nextLine();
        if (!Pattern.matches(Regex.CUSTOMER_NAME.get(), customerName))
            throw new WrongInputException("이름의 양식에 맞지 않습니다.");
        return customerName;
    }

    @Override
    public String customerEmailInput(String prompt) {
        System.out.println(prompt);
        String customerEmail = scanner.nextLine();
        if (!Pattern.matches(Regex.CUSTOMER_EMAIL.get(), customerEmail))
            throw new WrongInputException("이메일의 양식에 맞지 않습니다.");
        return customerEmail;
    }

    @Override
    public Optional<CustomerStatus> customerStatusInput(String prompt) {
        System.out.println(prompt);
        try {
            String userInput = scanner.nextLine();
            if (userInput.equals("")) return Optional.empty();
            return CustomerStatus
                    .getStatus(Integer.parseInt(userInput));
        } catch (NumberFormatException e) {
            throw new WrongInputException("숫자를 입력해주세요.");
        }
    }

    @Override
    public void informModuleMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Voucher Management ---\n");
        sb.append("Type %s to %s.\n".formatted(ModuleCommand.EXIT.getCommand(), "exit the program"));
        sb.append("Type %s to %s.\n".formatted(ModuleCommand.VOUCHER.getCommand(), "manage vouchers"));
        sb.append("Type %s to %s.\n".formatted(ModuleCommand.CUSTOMER.getCommand(), "manage customers"));
        sb.append("Type %s to %s.\n".formatted(ModuleCommand.WALLET.getCommand(), "manage voucher wallet"));
        System.out.print(sb);
    }

    @Override
    public void informCrudCommand(ModuleCommand moduleCommand) {
        StringBuilder sb = new StringBuilder();
        String module = moduleCommand.toString().toLowerCase();
        sb.append("--- %s ---\n".formatted(module));
        sb.append("Type %s to %s.\n".formatted(CrudCommand.CANCEL.getCommand(), "get back to menu"));
        sb.append("Type %s to %s %s.\n".formatted(CrudCommand.CREATE.getCommand(), "create a", module));
        sb.append("Type %s to %s %ss.\n".formatted(CrudCommand.LIST.getCommand(), "list all", module));
        sb.append("Type %s to %s %s.\n".formatted(CrudCommand.UPDATE.getCommand(), "update the", module));
        sb.append("Type %s to %s %s.\n".formatted(CrudCommand.DELETE.getCommand(), "delete the", module));
        System.out.print(sb);
    }

    @Override
    public void informWalletCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Voucher Wallet ---\n");
        sb.append("Type the number.");
        for (WalletCommand command : WalletCommand.values()) {
            sb.append("%s. %s : %s\n".formatted(command.ordinal() + 1, command.toString(), command.getDescription()));
        }
        System.out.print(sb);
    }

    @Override
    public void errorMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void exitMessage() {
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void cancelMessage() {
        System.out.println("이전 메뉴로 돌아갑니다.");
    }

    @Override
    public void completeMessage(String msg) {
        System.out.println(MessageFormat.format("{0} 완료되었습니다.", msg));
    }

    @Override
    public void informVoucherTypeFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Voucher Type ---\n");
        for (VoucherType type : VoucherType.values()) {
            sb.append("%s. %s\n".formatted(type.ordinal() + 1, type.toString()));
        }
        System.out.print(sb);
    }

    @Override
    public void informCustomerStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Customer Status ---\n");
        for (CustomerStatus status : CustomerStatus.values()) {
            sb.append("%s. %s\n".formatted(status.ordinal() + 1, status.toString()));
        }
        System.out.print(sb);
    }

    @Override
    public void infoMessage(String msg) {
        System.out.println(msg);
    }
}
