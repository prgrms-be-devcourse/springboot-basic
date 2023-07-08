package com.prgrms;

import com.prgrms.view.command.Command;
import com.prgrms.view.command.CommandFactory;
import com.prgrms.controller.VoucherController;
import com.prgrms.view.Menu;
import com.prgrms.view.ViewManager;
import com.prgrms.view.command.Power;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private final VoucherController voucherController;
    private final ViewManager viewManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommandLineApplication(VoucherController voucherController, ViewManager viewManager) {
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }

    @Override
    public void run(String... args) {
        Power power = Power.ON;
        CommandFactory commandFactory = new CommandFactory(voucherController, viewManager);
        while (power.isOn()) {
            try {
                Menu menu = viewManager.guideStartVoucher();
                Command command = menu.createCommand(commandFactory);
                power = command.execute();

            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                viewManager.viewError(e.getMessage());
            }
        }
    }
}
