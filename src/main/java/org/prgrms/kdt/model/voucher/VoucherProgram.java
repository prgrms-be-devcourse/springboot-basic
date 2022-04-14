package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.function.Function;
import org.prgrms.kdt.model.function.FunctionMapping;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherProgram implements Runnable {

    private Input input;
    private Output output;
    private VoucherService voucherService;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    public VoucherProgram(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean isEnd = false;

        while (!isEnd) {
            isEnd = execute();
        }
    }

    private boolean execute() {
        output.printFunctions();
        String inputFunction = input.function();
        if (!hasFunction(inputFunction)) {
            return false;
        }
        return getFunctionByName(inputFunction).execute(voucherService);
    }

    private boolean hasFunction(String inputFunction) {
        if (!Function.hasFunction(inputFunction)) {
            logger.info("input [Function] -> {}", inputFunction);
            output.printInputFunctionError();
            return false;
        }
        return true;
    }

    private Function getFunctionByName(String functionName) {
        return FunctionMapping.valueOf(functionName);
    }
}
