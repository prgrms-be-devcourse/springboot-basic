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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

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

            this.output.print(introMessage);

            Command command = this.input.read();

            try {
                this.commandValidator.validate(command);
            } catch (InvalidCommandException e) {
                this.output.print(e.getMessage());
                continue;
            }

            CommandType commandType = CommandType.of(command.getName());

            switch (commandType) {
                case CREATE:
                    this.output.print("=== 생성된 바우처 ===");
                    Voucher createdVoucher = this.voucherService.createVoucher(command.getOptions().get(0));
                    this.output.printVoucher(createdVoucher);
                    break;

                case LIST:
                    List<Voucher> vouchers = this.voucherService.getVouchers();
                    this.output.print("=== 생성된 바우처 목록 ===");
                    this.output.printVoucherList(vouchers);
                    break;

                case BLACK_LIST:
                    List<Customer> customers = this.customerService.getBannedCustomers();
                    this.output.printCustomerList(customers);
                    break;

                case EXIT:
                    this.output.print("EXIT!");
                    return;
            }
        }
    }
}
