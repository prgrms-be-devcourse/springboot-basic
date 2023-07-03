package com.programmers.application.controller.command.execution;

import java.io.IOException;

@FunctionalInterface
public interface VoucherExecution {
    void execute() throws IOException;
}
