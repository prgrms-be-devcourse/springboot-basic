package com.prgrms;

import com.prgrms.presentation.Menu;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.command.CommandCreator;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandLineApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Input input;
    private final Output output;
    private final CommandCreator commandCreator;

    public CommandLineApplication(Input input, Output output, CommandCreator commandCreator) {
        this.input = input;
        this.output = output;
        this.commandCreator = commandCreator;
    }

    @Override
    public void run(String... args) {
        Power power = Power.ON;

        while (power.isOn()) {
            try {
                Menu menu = guideStartVoucher();
                Command command = commandCreator.createCommand(menu);
                power = command.execute();
            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                output.write(e.getMessage());
            }
        }
    }

    private Menu guideStartVoucher() {
        output.write(GuideMessage.START.toString());
        String option = input.enterOption();
        return Menu.findByMenu(option);
    }
}
