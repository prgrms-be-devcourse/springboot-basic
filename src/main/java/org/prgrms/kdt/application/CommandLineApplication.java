package org.prgrms.kdt.application;

import org.prgrms.kdt.engine.customer.service.CustomerService;
import org.prgrms.kdt.engine.io.Console;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.service.VoucherService;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdt.engine"})
public class CommandLineApplication {
    private static final String PROFILE = "local";
    private static final Input input = new Console();
    private static final Output output = new Console();
    private static VoucherService voucherService;
    private static CustomerService customerService;
    private static String prompt;

    public static void main(String[] args) {
        var springApplication = new SpringApplication(CommandLineApplication.class);
        var applicationContext = springApplication.run(args);
        voucherService = applicationContext.getBean(VoucherService.class);
        customerService = applicationContext.getBean(CustomerService.class);

        output.help();
        while (true) {
            String command = input.inputCommand("Command : ");
            if (!CommandType.has(command)) {
                output.printIllegalInputError();
                continue;
            }

            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            switch (commandType) {
                case CREATE:
                    output.showVoucherOptions();
                    String typeName = input.inputCommand("");
                    Optional<Voucher> voucher = createVoucher(typeName);
                    voucher.ifPresentOrElse(output::createVoucher, output::printIllegalInputError);
                    break;

                case LIST:
                    Optional<Map<UUID, Voucher>> voucherList = voucherService.listVoucher();
                    voucherList.ifPresentOrElse(output::listVoucher, output::printVoucherListNotFoundError);
                    break;

                case ALLOCATE_CUSTOMER:
                    prompt = "comma separated UUID of voucher followed by UUID of customer : ";
                    var uuids = input.inputCommand(prompt).split(",");
                    voucherService.setVoucherCustomer(uuids);
                    output.allocateCustomer(uuids);
                    break;

                case LIST_CUSTOMER_VOUCHERS:
                    prompt = "UUID of the customer : ";
                    var customerId = input.inputCommand(prompt);
                    voucherList = voucherService.listCustomerVoucher(customerId);
                    voucherList.ifPresentOrElse(output::listVoucher, output::printVoucherListNotFoundError);
                    break;

                case DELETE_CUSTOMER_VOUCHER:
                    break;

                case GET_VOUCHER_OWNER:
                    break;

                case EXIT:
                    System.exit(0);
                    break;
            }
        }
    }

    private static Optional<Voucher> createVoucher(String typeName) {
        VoucherType type;
        long rate;

        try {
            type = VoucherType.valueOf(typeName.toUpperCase());
            rate = Long.parseLong(input.inputCommand("rate : "));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        return Optional.of(voucherService.createVoucher(type, rate));
    }
}
