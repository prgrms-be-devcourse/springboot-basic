package org.prgrms.prgrmsspring;


import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.domain.ControllerType;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PrgrmsSpringApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(PrgrmsSpringApplication.class,
                args);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);

        while (true) {
            Command command = commandLineView.getCommand();
            if (Command.isExit(command)) {
                break;
            }
            ControllerType controllerType = ControllerType.findControllerTypeForCommand(command);
            ApplicationController controller = controllerType.createController(applicationContext);
            controller.start(command);
        }
    }
}
