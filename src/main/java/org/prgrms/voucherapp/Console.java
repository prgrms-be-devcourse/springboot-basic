package org.prgrms.voucherapp;

import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.Command;
import org.prgrms.voucherapp.global.VoucherType;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
* Console : 입출력을 관리하는 클래스
* Q. discountAmount에 대한 예외처리를 discountAmount를 입력받았을 때 하는게 좋은지, discountAmount를 가지고 voucher를 생성하려고 할때 하는게 좋은지?
* 현재 코드는 전자인데, 다른 input 메소드들은 input 받은 값들에 대한 책임을 지고 예외처리를 하고 있어서 discountAmountInput메소드가 amount의 유효성에 대한 책임까지 지는게 일관성있다고 생각했음.
* 위 생각이 괜찮은 논리인건지 궁금합니다.
* */
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Command commandInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        return Command
                .getMenu(scanner.nextLine())
                .orElseThrow(() -> (new WrongInputException("존재하지 않는 명령어를 입력하였습니다.")));
    }

    @Override
    public VoucherType voucherTypeInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        try {
            return VoucherType
                    .getType(scanner.nextInt())
                    .orElseThrow(() -> (new WrongInputException("존재하지 않는 바우처 타입을 입력하였습니다.")));
        } catch (InputMismatchException e){
            throw new WrongInputException("숫자를 입력해주세요.");
        } finally{
            scanner.nextLine();
        }
    }

    @Override
    public long discountAmountInput(VoucherType voucherType, String prompt) throws WrongInputException, WrongAmountException {
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
    public void introMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===\n");
        for (Command command : Command.values()) {
            sb.append("Type %s to %s.\n".formatted(command.getCommand(), command.getAction()));
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
    public void informVoucherTypeFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Voucher Type ---\n");
        for (VoucherType type : VoucherType.values()) {
            sb.append("%s. %s\n".formatted(type.getOption(), type.toString()));
        }
        System.out.print(sb);
    }

    @Override
    public void infoMessage(String msg) {
        System.out.println(msg);
    }
}
