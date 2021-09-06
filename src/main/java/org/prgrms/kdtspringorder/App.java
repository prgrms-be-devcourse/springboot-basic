package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.customer.service.CustomerService;
import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.io.domain.Command;
import org.prgrms.kdtspringorder.io.enums.implementation.CommandType;
import org.prgrms.kdtspringorder.io.exception.InvalidCommandException;
import org.prgrms.kdtspringorder.io.validation.CommandValidator;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final CommandValidator commandValidator;


    public App(Input input, Output output, VoucherService voucherService,
               CustomerService customerService, CommandValidator commandValidator) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.commandValidator = commandValidator;
    }

    public void run() {
        while (true) {
            String introMessage =
                    new StringBuilder()
                            .append("=== Voucher Program ===\n")
                            .append("Type exit to exit the program.\n")
                            .append("Type create to create a new voucher.\n")
                            .append("Type black-list to list black list customers.\n")
                            .append("Type list to list all vouchers.").toString();

            output.print(introMessage);

            Command command = input.read();

            try {
                commandValidator.validate(command);
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
                continue;
            }

            CommandType commandType = CommandType.of(command.getCommandName());

            switch (commandType) {
                case CREATE:
                    output.print("=== 생성된 바우처 ===");
                    Voucher createdVoucher = voucherService.createVoucher(command.getOptions().get(0));
                    output.printVoucher(createdVoucher);
                    break;

                case LIST:
                    List<Voucher> vouchers = voucherService.getVouchers();
                    output.print("=== 생성된 바우처 목록 ===");
                    output.printVoucherList(vouchers);
                    break;

                case BLACK_LIST:
                    List<Customer> customers = customerService.getBannedCustomers();
                    output.printCustomerList(customers);
                    break;

                case EXIT:
                    output.print("EXIT!");
                    return;
            }
        }
    }
}
