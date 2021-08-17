package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.controller.CommandType;
import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.helper.MessageHelper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        InputController inputController = applicationContext.getBean(InputController.class);
        MessageHelper messageHelper = applicationContext.getBean(MessageHelper.class);

        voucherProcess(inputController, messageHelper);
    }

    public static void voucherProcess(InputController inputController, MessageHelper messageHelper) {
        messageHelper.showVoucherProgramGuideMessage();
        while(true){
            CommandType commandType = inputController.getUserInput();
            switch (commandType) {
                case EXIT -> {
                    messageHelper.showExitMessage();
                    System.exit(0);
                }
                case CREATE -> {
                    //do Something
                    break;
                }
                case LIST -> {
                    //do Something
                    break;
                }
                case REPLAY -> {
                    messageHelper.showVoucherProgramGuideMessage();
                    break;
                }
                default -> {
                    messageHelper.showRetryMessage();
                    break;
                }
            }
        }
    }
}