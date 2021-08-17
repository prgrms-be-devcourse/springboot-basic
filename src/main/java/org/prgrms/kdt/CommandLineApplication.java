package org.prgrms.kdt;

import org.prgrms.kdt.config.MissionConfiguration;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.io.command.Command;
import org.prgrms.kdt.io.command.Exit;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */
public class CommandLineApplication implements Runnable {

    private final Console console;
    private Command command;

    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        VoucherService voucherService = getVoucherService(getApplicationContext());
        console.guide();

        while (true) {
            switch (console.inputCommand()) {
                case EXIT -> command = new Exit();
            }

            command.execute();
        }
    }

    private AnnotationConfigApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext(MissionConfiguration.class);
    }

    private VoucherService getVoucherService(AnnotationConfigApplicationContext applicationContext) {
        return applicationContext.getBean(VoucherService.class);
    }
}
