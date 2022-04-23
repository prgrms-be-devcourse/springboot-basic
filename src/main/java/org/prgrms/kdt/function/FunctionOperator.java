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
import org.prgrms.kdt.service.VoucherWalletService;
import org.prgrms.kdt.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FunctionOperator {

    private BlackListService blackListService;
    private CustomerService customerService;
    private VoucherService voucherService;
    private VoucherWalletService voucherWalletService;
    private final static Logger logger = LoggerFactory.getLogger(Function.class);

    public FunctionOperator(BlackListService blackListService, CustomerService customerService, VoucherService voucherService, VoucherWalletService voucherWalletService) {
        this.blackListService = blackListService;
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    public void execute(String type) {
        switch (type) {
            case ("create") -> createVoucherByVoucherType();
            case ("voucherList") -> printVoucherList();
            case ("blackList") -> new OutputConsole().printList(blackListService.getBlackList());
            case ("add") -> createNewCustomer();
            case ("provide") -> provideVoucherToCustomer();
            case ("manage") -> printCustomerVoucherList();
        }
    }

    private void createVoucherByVoucherType() {
        Output output = new OutputConsole();
        output.printVoucherType();
        Input input = new InputConsole();

        try {
            voucherService.createVoucher(UUID.randomUUID(),
                    Utility.toInt(input.inputString()),
                    Utility.toInt(input.inputAmount()));
        } catch (IllegalArgumentException e) {
            logger.info("error -> {}", e.getMessage());
            output.printMessage(e.getMessage());
        }
    }

    private void printVoucherList() {
        Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
        if (voucherList.isEmpty()) {
            new OutputConsole().printMessage("voucher list is empty !!\n");
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

    private void provideVoucherToCustomer() {
        List<Voucher> voucherList = voucherService.getOwnableVoucherList();
        new OutputConsole().printList(voucherList);
        String voucherId = new InputConsole().inputString();

        List<Customer> customerList = customerService.getAllCustomers();
        new OutputConsole().printList(customerList);
        String customerId = new InputConsole().inputString();

        //vouchers table update
        Optional<Voucher> voucher = voucherService.provideVoucherToCustomer(voucherId, customerId);
        voucher.ifPresent(value -> new OutputConsole().printMessage(value.getVoucherId() + " is provided"));
    }

    private void printCustomerVoucherList() {
        new OutputConsole().printMessage("input customer Email");
        String customerEmail = new InputConsole().inputString();
        Optional<Map<UUID, Voucher>> voucherList = voucherWalletService.getVoucherListByCustomerEmail(customerEmail);
        voucherList.ifPresent(uuidVoucherMap -> new ArrayList<>(uuidVoucherMap.values()));
    }
//
//    private void deleteCheck() {
//        new OutputConsole().printMessage("Type D if you want delete ");
//    }


}
