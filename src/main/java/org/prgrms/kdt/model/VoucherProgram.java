package org.prgrms.kdt.model;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;

public class VoucherProgram implements Runnable {

    Input input;
    Output output;
    VoucherService voucherService;

    public VoucherProgram(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        while (true) {
            output.printFunctions();
            String inputFunction = input.inputFunction();
            if (!Function.hasFunction(inputFunction)) {
                output.printInputFunctionError();
                continue;
            }
            doFunction(inputFunction);
        }

    }

    public void doFunction(String function) {
        FunctionMapping.getFunction(function).doFunction(voucherService);
    }


}
