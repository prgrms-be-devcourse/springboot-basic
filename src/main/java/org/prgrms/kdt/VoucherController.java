package org.prgrms.kdt;

import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.io.OutputConstant;
import org.prgrms.kdt.utils.SelectType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements CommandLineRunner {

    private final IOManager ioManager;

    public VoucherController(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public void run(String... args) {
        while (true) {
            ioManager.doOutput(OutputConstant.CONSOLESTART);
            String selectTypeInput = ioManager.getInput();
            switch (SelectType.findSelectType(selectTypeInput)) {
                case CREATE -> {

                }
                case LIST -> {

                }
                case EXIT -> {
                    ioManager.doOutput(OutputConstant.CONSOLEEND);
                    return;
                }
                case NOTHING -> ioManager.doOutput(OutputConstant.SELECTWRONG);
            }
        }
    }
}
