package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.config.GuideType;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("command")
public class ConsoleCommandService implements CommandService {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleCommandService.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public ConsoleCommandService(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (true) {
            output.printGuide(GuideType.COMMAND.getMESSAGE());
            try {
                String inputCommand = input.readLine();
                Command command = Command.of(inputCommand);
                logger.info("select {}", command.name());
                execute(command);
            } catch (RuntimeException e) {
                logger.info("{}:{}",e.getClass(),e.getMessage());
            }
        }
    }

    @Override
    public void execute(Command command) {
        UUID customerId, voucherId;
        switch (command) {
            case CREATE_VOUCHER:
                VoucherRequest voucherRequest = input.inputVoucherTypeAndAmount();
                voucherService.createVoucher(voucherRequest);
                break;
            case LIST_VOUCHER:
                output.printList(voucherService.findAll());
                break;
            case CREATE_CUSTOMER:
                CustomerRequest customerRequest = input.inputCustomer();
                System.out.println(customerService.createCustomer(new Customer(customerRequest)));
                break;
            case UPDATE_CUSTOMER:
                customerId = input.inputCustomerId();
                String customerName = input.inputCustomerName();
                customerService.updateCustomer(customerId,customerName);
                break;
            case DELETE_CUSTOMER:
                customerId = input.inputCustomerId();
                customerService.deleteCustomer(customerId);
                break;
            case DELETE_ALL_CUSTOMER:
                customerService.deleteAllCustomer();
                break;
            case FIND_CUSTOMER_BY_ID:
                customerId = input.inputCustomerId();
                System.out.println(customerService.findById(customerId));
                break;
            case FIND_CUSTOMER_BY_EMAIL:
                String email = input.inputCustomerEmail();
                System.out.println(customerService.findByEmail(email));
                break;
            case LIST_CUSTOMER:
                output.printList(customerService.findAll());
                break;
            case ASSIGN_VOUCHER:
                voucherId = input.inputVoucherId();
                customerId = input.inputCustomerId();
                voucherService.updateByCustomerId(voucherId,customerId);
                break;
            case DELETE_VOUCHER:
                customerId = input.inputCustomerId();
                voucherService.deleteVoucherByCustomerId(customerId);
                break;
            case LIST_VOUCHER_WITH_TYPE:
                VoucherType voucherType = input.inputVoucherType();
                output.printList(voucherService.findCustomersByVoucherType(voucherType));
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
