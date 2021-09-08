package org.prgrms.kdt.application;

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
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.kdt.engine"})
public class CommandLineApplication {
    private static final Input input = new Console();
    private static final Output output = new Console();
    private static VoucherService voucherService;

    public static void main(String[] args) {
        var springApplication = new SpringApplication(CommandLineApplication.class);
        var applicationContext = springApplication.run(args);
        voucherService = applicationContext.getBean(VoucherService.class);

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
                    var prompt = "'fixed' for fixed voucher, 'percent' for percent voucher : ";
                    var typeName = input.inputCommand(prompt);
                    var voucher = createVoucher(typeName);
                    voucher.ifPresentOrElse(output::createVoucher, output::printIllegalInputError);
                    break;
                }

                case LIST: {
                    var voucherList = voucherService.listVoucher();
                    voucherList.ifPresentOrElse(output::listVoucher, output::printVoucherListNotFoundError);
                    break;
                }

                case ALLOCATE_CUSTOMER: {
                    var prompt = "comma separated UUID of voucher followed by UUID of customer : ";
                    var ids = input.inputCommand(prompt).split(",");

                    if (invalidIdFormat(ids)) {
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

                    if (invalidIdFormat(id)) {
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

                    if (invalidIdFormat(id)) {
                        output.printIllegalInputError();
                        break;
                    }

                    var customerId = UUID.fromString(id);
                    voucherService.deleteCustomerVoucher(customerId);
                    output.deleteCustomerVoucher(customerId);
                    break;
                }

                case GET_VOUCHER_OWNER: {
                    var prompt = "UUID of the voucher : ";
                    var id = input.inputCommand(prompt);

                    if (invalidIdFormat(id)) {
                        output.printIllegalInputError();
                        break;
                    }

                    var voucherId = UUID.fromString(id);
                    var customerId = voucherService.getVoucherOwner(voucherId);
                    customerId.ifPresentOrElse(output::voucherOwner, output::printVoucherOwnerNotFoundError);
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

    private static boolean invalidIdFormat(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    private static boolean invalidIdFormat(String[] ids) {
        try {
            Arrays.stream(ids).forEach(UUID::fromString);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
