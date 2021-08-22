package org.prgrms.kdt;

import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.CommandTypes;
import org.prgrms.kdt.command.Creation;
import org.prgrms.kdt.command.Listing;
import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.EnumMap;
import java.util.Map;

public class KdtApplication {

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherService = applicationContext.getBean(VoucherService.class);
        var console = new Console();

        var startMessage = "=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n";

        Map<CommandTypes, Command> commandMap = new EnumMap<>(CommandTypes.class);
        setCommandsMap(commandMap, console, voucherService);

        try {
            new CommandLineApplication(console, console).run(startMessage, commandMap);
        }
        catch (InvalidIOMessageException e) {
            System.err.println(e.getMessage());
        }

        applicationContext.close(); //ApplicationContext을 반드시 소멸시켜야 @PreConstruct 등의 콜백이 호출됨
    }

    private static void setCommandsMap(Map<CommandTypes, Command> commandMap, Console console, VoucherService voucherService) {
        commandMap.put(CommandTypes.CREATE, new Creation(console, console, voucherService));
        commandMap.put(CommandTypes.LIST, new Listing(console, console, voucherService));
    }

}
