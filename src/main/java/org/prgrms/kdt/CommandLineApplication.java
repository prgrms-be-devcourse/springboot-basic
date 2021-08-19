package org.prgrms.kdt;

import org.prgrms.kdt.common.AppConfiguration;
import org.prgrms.kdt.io.ConsoleInput;
import org.prgrms.kdt.io.ConsoleOutput;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.strategy.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.exception.Message.WRONG_COMMAND_MESSAGE;

public class CommandLineApplication {

    private static final Input inputView = new ConsoleInput();
    private static final Output outputView = new ConsoleOutput();

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        while (true) {
            inputView.inputHelp();
            String command = inputView.inputCommand();
            if (command.equalsIgnoreCase("exit")) {
                inputView.newLine();
                System.out.println("Bye");
                break;
            } else if (command.equalsIgnoreCase("create")) {
                inputView.newLine();
                processCreateCommand(voucherService);
            } else if (command.equalsIgnoreCase("list")) {
                inputView.newLine();
                processListCommand(voucherService);
            } else {
                inputView.newLine();
                System.out.println(WRONG_COMMAND_MESSAGE);
            }
            inputView.newLine();
        }
    }

    private static void processCreateCommand(VoucherService voucherService) {
        String voucherType = inputView.inputVoucherType();
        Voucher voucher = voucherService.create(voucherType);
        outputView.printVoucher(voucher);
    }

    private static void processListCommand(VoucherService voucherService) {
        List<Voucher> vouchers = voucherService.list();
        for (Voucher voucher : vouchers) {
            outputView.printVoucher(voucher);
        }
    }

}
