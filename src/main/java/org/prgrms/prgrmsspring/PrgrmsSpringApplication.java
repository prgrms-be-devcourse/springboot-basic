package org.prgrms.prgrmsspring;


import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.domain.ControllerManager;
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
        ControllerManager controllerManager = applicationContext.getBean(ControllerManager.class);

        while (true) {
            int modeNumber = commandLineView.getModeNumber();
            if (controllerManager.isExit(modeNumber)) {
                break;
            }
            ApplicationController controller = controllerManager.getController(modeNumber);
            controller.listMode();
            controller.run(commandLineView.inputCommand());
        }
    }
}
