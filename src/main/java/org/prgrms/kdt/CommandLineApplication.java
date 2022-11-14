package org.prgrms.kdt;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.io.IOManager;
import org.prgrms.kdt.manager.VoucherAppManager;
import org.prgrms.kdt.utils.SelectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

    private final VoucherAppManager voucherAppManager;
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final IOManager ioManager;

    public CommandLineApplication(VoucherAppManager voucherAppManager, IOManager ioManager) {
        this.voucherAppManager = voucherAppManager;
        this.ioManager = ioManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandLineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (voucherAppManager.isRunning()) {
            ioManager.writeStartMessage();
            try {
                SelectType selectType = SelectType.findSelectType(ioManager.getInput());
                voucherAppManager.execute(selectType);
            } catch (InvalidITypeInputException e) {
                logger.error("사용자가 유효하지 않은 값을 입력하였습니다.");
                ioManager.writeExceptionMessage(e.getMessage());
            }
        }
        logger.info("프로그램이 정상적으로 종료되었습니다.");
        ioManager.writeEndMessage();
    }
}
