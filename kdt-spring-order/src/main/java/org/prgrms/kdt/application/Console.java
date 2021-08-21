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

    }

    @Override
    public void printInputCommandError() {

    }
}
