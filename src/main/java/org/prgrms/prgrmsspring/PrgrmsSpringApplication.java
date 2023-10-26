package org.prgrms.prgrmsspring;


import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.domain.FunctionType;
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
            FunctionType functionType = commandLineView.getModeNumber();
            if (FunctionType.isExit(functionType)) {
                break;
            }
            ApplicationController controller = functionType.createController(applicationContext);
            controller.listMode();
            controller.run(commandLineView.inputCommand());
        }
    }
}
