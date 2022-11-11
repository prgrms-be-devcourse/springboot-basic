package org.prgrms.kdt;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.manager.VoucherAppManager;
import org.prgrms.kdt.utils.SelectType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements CommandLineRunner {
    private final IOManager ioManager;
    private final VoucherAppManager voucherAppManager;

    public VoucherController(IOManager ioManager, VoucherAppManager voucherAppManager) {
        this.ioManager = ioManager;
        this.voucherAppManager = voucherAppManager;
    }

    @Override
    public void run(String... args) throws Exception {
        while (voucherAppManager.isRunning()) {
            ioManager.writeStartMessage();
            try {
                SelectType selectType = SelectType.findSelectType(ioManager.getInput());
                voucherAppManager.execute(selectType);
            } catch (WrongSelectException e) {
                ioManager.writeExceptionMessage(e.getMessage());
            }
        }
        ioManager.writeEndMessage();
    }
}
