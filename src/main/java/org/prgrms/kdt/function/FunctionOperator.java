package org.prgrms.kdt.function;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class FunctionOperator {

    private BlackListService blackListService;
    private CustomerService customerService;
    private VoucherService voucherService;
    private final static Logger logger = LoggerFactory.getLogger(Function.class);

    public FunctionOperator(BlackListService blackListService, CustomerService customerService, VoucherService voucherService) {
        this.blackListService = blackListService;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    public void execute(String type) {
        switch (type) {
            case ("create"):
                createVoucherByVoucherType();
                break;
            case ("voucherList"):
                printVoucherList();
                break;
            case ("blackList"):
                new OutputConsole().printList(blackListService.getBlackList());
                break;
            case ("add"):
                createNewCustomer();
                break;
        }
    }

    private void createVoucherByVoucherType() {
        //입출력
        Output output = new OutputConsole();
        output.printVoucherType();
        Input input = new InputConsole();

        try {
            voucherService.createVoucher(UUID.randomUUID(),
                    Utility.toInt(input.inputVoucherType()),
                    Utility.toInt(input.inputAmount()));
        } catch (IllegalArgumentException e) {
            logger.info("error -> {}", e.getMessage());
            output.printExceptionMessage(e.getMessage());
        }
    }

    private void printVoucherList() {
        Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
        if (voucherList.isEmpty()) {
            new OutputConsole().printVoucherListEmptyError();
            return;
        }
        for (Map.Entry<UUID, Voucher> entry : voucherList.entrySet()) {
            Voucher voucher = entry.getValue();
            System.out.println(voucher.toString());
        }
    }

    private void createNewCustomer() {
        Input input = new InputConsole();
        String name = input.inputCustomerName();
        String email = input.inputCustomerEmail();
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
        customerService.join(customer);
    }

}
