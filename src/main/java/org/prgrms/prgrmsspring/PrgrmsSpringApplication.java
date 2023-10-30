package org.prgrms.prgrmsspring;


import lombok.RequiredArgsConstructor;
import org.prgrms.prgrmsspring.console.ConsoleIOManager;
import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.domain.ControllerManager;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@RequiredArgsConstructor
public class PrgrmsSpringApplication {
    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(PrgrmsSpringApplication.class, args);
    }

    @Profile(value = "command")
    @Bean
    public void init() {
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        ControllerManager controllerManager = applicationContext.getBean(ControllerManager.class);
        while (true) {
            int modeNumber = commandLineView.getModeNumber();
            if (controllerManager.isExit(modeNumber)) {
                break;
            }
            ApplicationController controller = controllerManager.getController(modeNumber);
            ConsoleIOManager consoleIoManager = controllerManager.getConsoleIoManager(controller);
            consoleIoManager.printCommands();
            consoleIoManager.run(commandLineView.inputCommand());
        }
    }
}
