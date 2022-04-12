package org.prgrms.voucherapp;

import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.Command;
import org.prgrms.voucherapp.global.VoucherType;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Command commandInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        return Command
                .getMenu(scanner.nextLine())
                .orElseThrow(()->(new WrongInputException("존재하지 않는 메뉴입니다.")));
    }

    @Override
    public VoucherType voucherTypeInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        return VoucherType
                .getType(scanner.nextLine())
                .orElseThrow(()->(new WrongInputException("존재하지 않는 바우처 타입입니다.")));
    }

    @Override
    public long discountAmountInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        long discountAmount = -1;
        try{
            discountAmount = scanner.nextLong();
        }catch(InputMismatchException e){
            throw new WrongInputException("숫자를 입력해주세요.");
        }finally {
            scanner.nextLine(); //버퍼비우기
        }
        return discountAmount;
    }

    @Override
    public void introProgram() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===\n");
        for(Command command : Command.values()){
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
        for(VoucherType type : VoucherType.values()){
            sb.append("%s. %s\n".formatted(type.getOption(), type.toString()));
        }
        System.out.print(sb);
    }
}
