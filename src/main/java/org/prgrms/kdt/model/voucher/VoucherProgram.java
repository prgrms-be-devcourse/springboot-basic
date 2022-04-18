package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.function.Function;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherProgram implements Runnable {

    private Input input;
    private Output output;
    private VoucherService voucherService;
    private BlackListService blackListService;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    public VoucherProgram(Input input, Output output, VoucherService voucherService, BlackListService blackListService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.blackListService = blackListService;
    }

    @Override
    public void run() {
        while (!execute()) {

        }
    }

    private boolean execute() {
        output.printFunctions();
        String inputFunction = input.inputFunction();
        if (!hasFunction(inputFunction)) {
            return false;
        }
        return getFunctionByName(inputFunction).execute(voucherService, blackListService);
    }

    private boolean hasFunction(String inputFunction) {
        try{
            Function.valueOf(inputFunction);
        }catch (IllegalArgumentException e) {
            output.printInputFunctionError();
            return false;
        }finally {
            logger.info("input [Function] -> {}", inputFunction);
        }
        return true;
    }

    private Function getFunctionByName(String functionName) {
        return Function.valueOf(functionName);
    }
}
