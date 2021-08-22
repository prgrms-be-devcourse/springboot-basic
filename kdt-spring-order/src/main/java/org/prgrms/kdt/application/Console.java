package org.prgrms.kdt.application;

import org.prgrms.kdt.application.voucher.command.CommandType;
import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner sc= new Scanner(System.in);

    @Override
    public CommandType inputCommand() {
        System.out.print("명령어 번호 입력 : ");
        String input = sc.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException ignored) {}
        return CommandType.fromOrdinal(choice);
    }

    @Override
    public void printProgramName() {
        System.out.println("\n======== 바우처 프로그램 ========");
    }

    @Override
    public void printCommandList() {
        String commandList = "\n원하는 명령어 번호를 입력해주세요.\n" +
                CommandType.getListOfEnum();
        System.out.println(commandList);
    }

    @Override
    public void printInputCommandError() {
        System.out.println("잘못된 입력입니다! 다시 입력해주세요~");
    }
}
