package org.prgrms.kdt;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.io.OutputConstant;
import org.prgrms.kdt.utils.SelectType;
import org.prgrms.kdt.utils.VoucherAppManager;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private final IOManager ioManager;
    private final VoucherAppManager voucherAppManager;

    public VoucherController(IOManager ioManager, VoucherAppManager voucherAppManager) {
        this.ioManager = ioManager;
        this.voucherAppManager = voucherAppManager;
    }

    public void execute() {
        while (voucherAppManager.getAppStatus()) {
            ioManager.doOutput(OutputConstant.CONSOLESTART);
            try {
                SelectType selectType = SelectType.findSelectType(ioManager.getInput());
                doProcess(selectType);
            } catch (WrongSelectException e) {
                ioManager.doOutput(OutputConstant.SELECTWRONG);
            }
        }
    }

    private void doProcess(SelectType selectType) {
        switch (selectType) {
            case CREATE -> {

            }
            case LIST -> {

            }
            case EXIT -> {
                ioManager.doOutput(OutputConstant.CONSOLEEND);
                voucherAppManager.changeAppStatusToStop();
            }
            default -> System.out.println(OutputConstant.SELECTWRONG);
        }
    }
}
