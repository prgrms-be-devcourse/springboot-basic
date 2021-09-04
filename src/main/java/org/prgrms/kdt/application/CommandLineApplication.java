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

import java.util.Arrays;
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
                case CREATE: {
                    String prompt = "'fixed' for fixed voucher, 'percent' for percent voucher : ";
                    String typeName = input.inputCommand(prompt);
                    Optional<Voucher> voucher = createVoucher(typeName);
                    voucher.ifPresentOrElse(output::createVoucher, output::printIllegalInputError);
                    break;
                }

                case LIST: {
                    Optional<Map<UUID, Voucher>> voucherList = voucherService.listVoucher();
                    voucherList.ifPresentOrElse(output::listVoucher, output::printVoucherListNotFoundError);
                    break;
                }

                case ALLOCATE_CUSTOMER: {
                    var prompt = "comma separated UUID of voucher followed by UUID of customer : ";
                    var ids = input.inputCommand(prompt).split(",");

                    if (!isIdValid(ids)) {
                        output.printIllegalInputError();
                        break;
                    }

                    var voucherId = UUID.fromString(ids[0]);
                    var customerId = UUID.fromString(ids[1]);
                    voucherService.setVoucherCustomer(voucherId, customerId);
                    output.allocateCustomer(voucherId, customerId);
                    break;
                }

                case LIST_CUSTOMER_VOUCHERS: {
                    var prompt = "UUID of the customer : ";
                    var id = input.inputCommand(prompt);

                    if (!isIdValid(id)) {
                        output.printIllegalInputError();
                        break;
                    }

                    var customerId = UUID.fromString(id);
                    var voucherList = voucherService.listCustomerVoucher(customerId);
                    voucherList.ifPresentOrElse(output::listVoucher, output::printVoucherListNotFoundError);
                    break;
                }

                case DELETE_CUSTOMER_VOUCHER: {
                    var prompt = "UUID of the customer : ";
                    var id = input.inputCommand(prompt);

                    if (!isIdValid(id)) {
                        output.printIllegalInputError();
                        break;
                    }

                    var customerId = UUID.fromString(id);
                    voucherService.deleteCustomerVoucher(customerId);
                    output.deleteCustomerVoucher(customerId);
                    break;
                }

                case GET_VOUCHER_OWNER: {
                    break;
                }

                case EXIT: {
                    System.exit(0);
                    break;
                }
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

    private static boolean isIdValid(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private static boolean isIdValid(String[] ids) {
        try {
            Arrays.stream(ids).forEach(UUID::fromString);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
