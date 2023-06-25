package org.prgrms.kdt;

import org.prgrms.kdt.input.MenuCommand;
import org.prgrms.kdt.input.UserInput;
import org.prgrms.kdt.input.UserInputMenu;
import org.prgrms.kdt.output.Output;
import org.prgrms.kdt.output.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherApp {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Output outputConsole = ac.getBean(Output.class);
        UserInput userInputMenu = ac.getBean(UserInput.class);
        while (true) {
            outputConsole.displayMenu();
            outputConsole.displayUserInputLine();
            String userInputMenuCommand = userInputMenu.userInputMenuCommand();
            MenuCommand UserInputMenuCommand = MenuCommand.findByUserInputMenuCommand(userInputMenuCommand);
            switch (UserInputMenuCommand) {
                case EXIT -> {
                    return;
                }
                case CREATE -> {

                }
                case LIST -> {
                }
            }
        }
    }
}
