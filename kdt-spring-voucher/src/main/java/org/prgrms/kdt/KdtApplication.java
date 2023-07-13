package org.prgrms.kdt;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.input.MenuCommand;
import org.prgrms.kdt.input.UserInput;
import org.prgrms.kdt.input.VoucherCommand;
import org.prgrms.kdt.output.Output;
import org.prgrms.kdt.storage.VoucherStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);
        Output outputConsole = applicationContext.getBean(Output.class);
        UserInput userInputMenu = applicationContext.getBean(UserInput.class);
        VoucherController voucherController = applicationContext.getBean(VoucherController.class);
        VoucherStorage voucherStorage = applicationContext.getBean(VoucherStorage.class);

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
                    outputConsole.displayVoucherCreateMenu();
                    outputConsole.displayUserInputLine();
                    String userInputVoucherCreateMenuCommand = userInputMenu.userInputVoucherCreateMenuCommand();
                    VoucherCommand UserInputVoucherCommand = VoucherCommand.findByUserInputVoucherCommand(userInputVoucherCreateMenuCommand);
                    createVoucher(outputConsole, voucherController, UserInputVoucherCommand);
                }
                case LIST -> outputConsole.displayAllVoucherList(voucherStorage.findAllVoucher());
                case WRONG -> outputConsole.userInputWrongValue();
            }
        }
    }

    private static void createVoucher(Output outputConsole, VoucherController voucherController, VoucherCommand UserInputVoucherCommand) {
        try {
            voucherController.createVoucher(UserInputVoucherCommand);
        } catch (IllegalArgumentException e) {
            outputConsole.displayError(e);
        }
    }
}
