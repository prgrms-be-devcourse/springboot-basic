package org.prgrms.kdt.model;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherProgram implements Runnable {

    Input input;
    Output output;
    VoucherService voucherService;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

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
                logger.info("input [Function] -> {}", inputFunction);
                output.printInputFunctionError();
                continue;
            }
            if ((Boolean) doFunction(inputFunction)) {
                return;
            }
        }
    }

    public Object doFunction(String function) {
        return FunctionMapping.getFunction(function).doFunction(voucherService);

    }
}
