package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.function.Function;
import org.prgrms.kdt.function.FunctionOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherProgram implements Runnable {

    private Output output;
    private FunctionOperator functionOperator;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    public VoucherProgram(Output output, FunctionOperator functionOperator) {
        this.output = output;
        this.functionOperator = functionOperator;
    }

    @Override
    public void run() {
        while (!execute()) {

        }
    }

    private boolean execute() {
        output.printFunctions();
        String inputFunction = new InputConsole().inputString();
        if (!hasFunction(inputFunction)) {
            return false;
        }
        return getFunctionByName(inputFunction).execute(functionOperator);
    }

    private boolean hasFunction(String inputFunction) {
        try{
            Function.valueOf(inputFunction);
        }catch (IllegalArgumentException e) {
            output.printMessage("WRONG : Type right command\n");
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
