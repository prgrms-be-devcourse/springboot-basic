package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.config.GuideType;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.dto.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
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
                execute(command);
            } catch (RuntimeException e) {
                logger.info("{}:{}", e.getClass(), e.getMessage());
            }
        }
    }

    @Override
    public void execute(Command command) {
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
                Customer customer = new Customer(customerRequest);
                output.printSingle(customerService.createCustomer(customer).toString());
                break;
            case UPDATE_CUSTOMER:
                UUID updateCustomerId = input.inputCustomerId();
                String customerName = input.inputCustomerName();
                customerService.updateCustomer(updateCustomerId, customerName);
                break;
            case DELETE_CUSTOMER:
                UUID customerId = input.inputCustomerId();
                customerService.deleteCustomer(customerId);
                break;
            case DELETE_ALL_CUSTOMER:
                customerService.deleteAllCustomer();
                break;
            case FIND_CUSTOMER_BY_ID:
                UUID customerById = input.inputCustomerId();
                output.printSingle(customerService.findById(customerById).toString());
                break;
            case FIND_CUSTOMER_BY_EMAIL:
                String email = input.inputCustomerEmail();
                output.printSingle(customerService.findByEmail(email).toString());
                break;
            case LIST_CUSTOMER:
                output.printList(customerService.findAll());
                break;
            case ASSIGN_VOUCHER:
                UUID voucherId = input.inputVoucherId();
                UUID assignCustomerId = input.inputCustomerId();
                voucherService.updateByCustomerId(voucherId, assignCustomerId);
                break;
            case DELETE_VOUCHER:
                UUID deleteCustomerId = input.inputCustomerId();
                voucherService.deleteVoucherByCustomerId(deleteCustomerId);
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
