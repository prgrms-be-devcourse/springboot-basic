package org.prgrms.kdt;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.manager.VoucherAppManager;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.SelectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "local"})
public class VoucherConsoleApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleApp.class);

    private final VoucherAppManager voucherAppManager;
    private final IOManager ioManager;

    public VoucherConsoleApp(VoucherAppManager voucherAppManager, IOManager ioManager) {
        this.voucherAppManager = voucherAppManager;
        this.ioManager = ioManager;
    }

    @Override
    public void run(String... args) throws Exception {
        Power power = new Power();
        while (power.getIsRunning()) {
            ioManager.writeStartMessage();
            try {
                SelectType selectType = SelectType.findSelectType(ioManager.getInput());
                voucherAppManager.execute(selectType, power);
            } catch (InvalidITypeInputException e) {
                logger.error("사용자가 유효하지 않은 값을 입력하였습니다.");
                ioManager.writeExceptionMessage(e.getMessage());
            }
        }
        logger.info("프로그램이 종료되었습니다.");
        if (power.getIsExceptionExit()) {
            ioManager.writeMessage("프로그램 오류로 종료되었습니다.");
        }
        ioManager.writeEndMessage();
    }
}
