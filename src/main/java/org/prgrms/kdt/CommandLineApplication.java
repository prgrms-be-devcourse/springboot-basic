package org.prgrms.kdt;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.common.AppConfiguration;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.ConsoleInput;
import org.prgrms.kdt.io.ConsoleOutput;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.SimpleCustomerService;
import org.prgrms.kdt.service.SimpleVoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.prgrms.kdt.error.Message.WRONG_COMMAND_MESSAGE;

@Slf4j
public class CommandLineApplication {

    private static final Input inputView = new ConsoleInput();
    private static final Output outputView = new ConsoleOutput();

    public static void main(String[] args) {
        log.info("[*]");
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(SimpleVoucherService.class);
        log.info("[*] voucher service: {}", voucherService);
        var customerService = applicationContext.getBean(SimpleCustomerService.class);
        while (true) {
            inputView.inputHelp();
            String command = inputView.inputCommand();
            inputView.newLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Bye");
                break;
            } else if (command.equalsIgnoreCase("create")) {
                processCreateCommand(voucherService);
            } else if (command.equalsIgnoreCase("list")) {
                processListCommand(voucherService);
            } else if (command.equalsIgnoreCase("allocate")) {
                processAllocateCommand(voucherService);
            } else if (command.equalsIgnoreCase("customers")) {
                processCustomersCommand(customerService);
            } else {
                System.out.println(WRONG_COMMAND_MESSAGE);
            }
            inputView.newLine();
        }
        applicationContext.close();
    }

    private static void processCustomersCommand(SimpleCustomerService customerService) {
        List<Customer> customers = customerService.list();
        for (Customer customer : customers) {
            outputView.printCustomers(customer);
        }
    }

    private static void processAllocateCommand(SimpleVoucherService voucherService) {
        Long voucherId = inputView.inputVoucherId();
        Long customerId = inputView.inputCustomerId();
        Voucher voucher = voucherService.allocate(voucherId, customerId);
        outputView.printVoucher(voucher);
    }

    private static void processCreateCommand(SimpleVoucherService voucherService) {
        String voucherType = inputView.inputVoucherType();
        Voucher voucher = voucherService.create(voucherType);
        outputView.printVoucher(voucher);
    }

    private static void processListCommand(SimpleVoucherService voucherService) {
        List<Voucher> vouchers = voucherService.list();
        for (Voucher voucher : vouchers) {
            outputView.printVoucher(voucher);
        }
    }

}
