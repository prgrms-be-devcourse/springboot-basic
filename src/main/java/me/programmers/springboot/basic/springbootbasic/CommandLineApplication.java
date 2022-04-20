package me.programmers.springboot.basic.springbootbasic;

import me.programmers.springboot.basic.springbootbasic.command.Command;
import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.command.CommandType;
import me.programmers.springboot.basic.springbootbasic.command.ExitCommand;
import me.programmers.springboot.basic.springbootbasic.command.WrongCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerDeleteCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerFindAllCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerFindByEmailCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerInsertCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerUpdateCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.CreateVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.ShowVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.io.In;
import me.programmers.springboot.basic.springbootbasic.io.Out;
import me.programmers.springboot.basic.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final AnnotationConfigApplicationContext context;

    public CommandLineApplication(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    public void run() {
        logger.info("CommandLineApplication Start");
        ConsoleOutput outputConsole = new Out();
        ConsoleInput inputConsole = new In();

        boolean isExit = false;
        while (!isExit) {
            showMenu(outputConsole);
            String inputMenu = inputConsole.inputCommand("명령어 입력: ");
            CommandType commandType = getCommand(inputMenu);
            isExit = doCommand(commandType);
        }
    }

    private boolean doCommand(CommandType commandType) {
        if (commandType == null)
            return false;

        VoucherService voucherService = context.getBean(VoucherService.class);
        CustomerService customerService = context.getBean(CustomerService.class);

        boolean isExit = false;
        CommandStrategy commandStrategy = null;
        switch (commandType) {
            case EXIT:
                isExit = true;
                commandStrategy = new ExitCommand();
                break;
            case CREATE:
                commandStrategy = new CreateVoucherCommand(voucherService);
                break;
            case LIST:
                commandStrategy = new ShowVoucherCommand(voucherService);
                break;
            case CUSTOMER_INSERT:
                commandStrategy = new CustomerInsertCommand(customerService);
                break;
            case CUSTOMER_UPDATE:
                commandStrategy = new CustomerUpdateCommand(customerService);
                break;
            case CUSTOMER_LIST:
                commandStrategy = new CustomerFindAllCommand(customerService);
                break;
            case CUSTOMER_FINDBY_EMAIL:
                commandStrategy = new CustomerFindByEmailCommand(customerService);
                break;
            case CUSTOMER_DELETE:
                commandStrategy = new CustomerDeleteCommand(customerService);
                break;
            default:
                commandStrategy = new WrongCommand();
                break;
        }

        Command command = new Command(commandStrategy);
        command.operateCommand();
        return isExit;
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
                "1. Type exit to exit the program.\n" +
                "2. Type create to create a new voucher.\n" +
                "3. Type list to list all vouchers.\n" +
                "4. Type customer_insert to create a new customer\n" +
                "5. Type customer_list to find all customers\n" +
                "6. Type customer_update to update customer\n" +
                "7. Type customer_delete to delete all customers\n"
        );
    }
}
