package com.prgrms;

import com.prgrms.view.Menu;
import com.prgrms.view.ViewManager;
import com.prgrms.view.command.Command;
import com.prgrms.view.command.CommandFactory;
import com.prgrms.view.command.Power;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final ViewManager viewManager;
    private final CommandFactory commandFactory;

    public CommandLineApplication(ViewManager viewManager, CommandFactory commandFactory) {
        this.viewManager = viewManager;
        this.commandFactory = commandFactory;
    }

    @Override
    public void run(String... args) {
        Power power = Power.ON;

        while (power.isOn()) {
            try {
                Menu menu = viewManager.guideStartVoucher();
                Command command = commandFactory.createCommand(menu);
                command.execute();
            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                viewManager.viewError(e.getMessage());
            }
        }
    }
}
