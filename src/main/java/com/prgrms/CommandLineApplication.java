package com.prgrms;

import com.prgrms.presentation.Menu;
import com.prgrms.presentation.view.ViewManager;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.command.CommandCreator;
import com.prgrms.presentation.command.Power;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final ViewManager viewManager;
    private final CommandCreator commandCreator;

    public CommandLineApplication(ViewManager viewManager, CommandCreator commandCreator) {
        this.viewManager = viewManager;
        this.commandCreator = commandCreator;
    }

    @Override
    public void run(String... args) {
        Power power = Power.ON;

        while (power.isOn()) {
            try {
                Menu menu = viewManager.guideStartVoucher();
                Command command = commandCreator.createCommand(menu);
                power = command.execute(viewManager);
            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                viewManager.viewError(e.getMessage());
            }
        }
    }
}
