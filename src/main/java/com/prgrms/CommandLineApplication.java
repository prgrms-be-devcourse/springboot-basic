package com.prgrms;

import com.prgrms.presentation.Menu;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("console")
@Component
public class CommandLineApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;

    public CommandLineApplication(Input input, Output output,
            ApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        Power power = Power.ON;

        while (power.isOn()) {
            try {
                Menu menu = guideStartVoucher();
                Command strategy = applicationContext.getBean(menu.getBeanName(), Command.class);
                strategy.execute();
            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못,된 입력이 발생하였습니다. {0}", e);
                output.write(e.getMessage());
            } catch (Exception e) {
                logger.error("알 수 없는 error 발생");
            }
        }
    }

    private Menu guideStartVoucher() {
        output.write(GuideMessage.START.toString());
        String option = input.enterOption();
        return Menu.findByMenu(option);
    }

}
