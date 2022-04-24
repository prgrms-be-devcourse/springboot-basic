package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.function.VoucherProgramFunctions;
import org.prgrms.kdt.function.FunctionOperator;
import org.prgrms.kdt.io.OutputConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherProgram implements Runnable {

    private final FunctionOperator functionOperator;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    public VoucherProgram(FunctionOperator functionOperator) {
        this.functionOperator = functionOperator;
    }

    @Override
    public void run() {
        while (!executeProgram()) {

        }
    }

    private boolean executeProgram() {
        new OutputConsole().printFunctions();
        String inputFunction = new InputConsole().inputString();
        if (!hasFunction(inputFunction)) {
            return false;
        }
        return getFunctionByName(inputFunction).execute(functionOperator);
    }

    private boolean hasFunction(String inputFunction) {
        try{
            VoucherProgramFunctions.valueOf(inputFunction);
        }catch (IllegalArgumentException e) {
            OutputConsole.printMessage("WRONG : Type right command\n");
            return false;
        }finally {
            logger.info("input [Function] -> {}", inputFunction);
        }
        return true;
    }

    private VoucherProgramFunctions getFunctionByName(String functionName) {
        return VoucherProgramFunctions.valueOf(functionName);
    }
}
