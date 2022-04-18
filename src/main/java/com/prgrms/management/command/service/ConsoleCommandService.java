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
                logger.info("{}:{}",e.getClass(),e.getMessage());
            }
        }
    }

    @Override
    public void execute(Command command) {
        UUID customerId, voucherId;
        switch (command) {
            case CREATEVOUCHER:
                VoucherRequest voucherRequest = input.inputVoucherTypeAndAmount();
                voucherService.createVoucher(voucherRequest);
                break;
            case LISTVOUCHER:
                output.printList(voucherService.findAll());
                break;
            case CREATECUSTOMER:
                CustomerRequest customerRequest = input.inputCustomer();
                System.out.println(customerService.createCustomer(new Customer(customerRequest)));
                break;
            case UPDATECUSTOMER:
                customerId = input.inputCustomerId();
                String customerName = input.inputCustomerName();
                customerService.updateCustomer(customerId,customerName);
                break;
            case DELETECUSTOMER:
                customerId = input.inputCustomerId();
                customerService.deleteCustomer(customerId);
                break;
            case DELETEALLCUSTOMER:
                customerService.deleteAllCustomer();
                break;
            case FINDCUSTOMERBYID:
                customerId = input.inputCustomerId();
                System.out.println(customerService.findById(customerId));
                break;
            case FINDCUSTOMERBYEMAIL:
                String email = input.inputCustomerEmail();
                System.out.println(customerService.findByEmail(email));
                break;
            case LISTCUSTOMER:
                output.printList(customerService.findAll());
                break;
            case ASSIGNVOUCHER:
                voucherId = input.inputVoucherId();
                customerId = input.inputCustomerId();
                voucherService.createVoucherByCustomerId(voucherId,customerId);
                break;
            case DELETEVOUCHER:
                customerId = input.inputCustomerId();
                voucherService.deleteVoucherByCustomerId(customerId);
                break;
            case LISTVOUCHERWITHTYPE:
                VoucherType voucherType = input.inputVoucherType();
                output.printList(voucherService.findCustomersByVoucherType(voucherType));
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
