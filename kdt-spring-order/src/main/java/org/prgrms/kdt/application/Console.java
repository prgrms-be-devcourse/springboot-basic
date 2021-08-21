package org.prgrms.kdt.application;

import org.prgrms.kdt.application.voucher.command.CommandType;
import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;

public class Console implements Input, Output {

    @Override
    public CommandType inputCommand() {
        return null;
    }

    @Override
    public void printCommandList() {
        String commandList = "\n\n======== 바우처 프로그램 ========\n\n" +
                "원하는 명령어 번호를 입력해주세요.\n" +
                CommandType.getListOfEnum();
        System.out.println(commandList);
    }

    @Override
    public void printInputCommandError() {

    }
}
