package me.programmers.springboot.basic.springbootbasic;

import me.programmers.springboot.basic.springbootbasic.command.Command;
import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.command.CommandType;
import me.programmers.springboot.basic.springbootbasic.command.CreateCommand;
import me.programmers.springboot.basic.springbootbasic.command.ExitCommand;
import me.programmers.springboot.basic.springbootbasic.command.ShowVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.command.WrongCommand;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.In;
import me.programmers.springboot.basic.springbootbasic.io.Out;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public void run() {
        logger.info("CommandLineApplication Start");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        VoucherService voucherService = context.getBean(VoucherService.class);
        ConsoleOutput outputConsole = new Out();
        ConsoleInput inputConsole = new In();

        boolean isExit = false;
        while (!isExit) {
            showMenu(outputConsole);
            String inputMenu = inputConsole.inputCommand("명령어 입력: ");

            CommandType commandType = getCommand(inputMenu);
            if (commandType == null) {
                continue;
            }

            CommandStrategy commandStrategy = null;
            switch (commandType) {
                case EXIT:
                    isExit = true;
                    commandStrategy = new ExitCommand();
                    break;
                case CREATE:
                    commandStrategy = new CreateCommand(voucherService);
                    break;
                case LIST:
                    commandStrategy = new ShowVoucherCommand(voucherService);
                    break;
                default:
                    commandStrategy = new WrongCommand();
                    break;
            }
            Command command = new Command(commandStrategy);
            command.operateCommand();
        }

        logger.info("CommandLineApplication End");
    }

    private CommandType getCommand(String inputMenu) {
        CommandType type = null;
        try {
            type = CommandType.getCommand(inputMenu);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 메뉴 명령어 입력: {}", inputMenu);
        }
        return type;
    }

    private void showMenu(ConsoleOutput out) {
        out.output("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }
}
