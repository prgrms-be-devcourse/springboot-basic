package org.prgrms.kdt.function;

import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.Customers;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.Vouchers;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.VoucherWalletService;
import org.prgrms.kdt.util.IntUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class FunctionOperator {

    private final BlackListService blackListService;
    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherWalletService voucherWalletService;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgramFunctions.class);
    private final static InputConsole inputConsole = new InputConsole();
    private final static String DELETE_CHARACTER = "D";

    public FunctionOperator(BlackListService blackListService, CustomerService customerService, VoucherService voucherService, VoucherWalletService voucherWalletService) {
        this.blackListService = blackListService;
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherWalletService = voucherWalletService;
    }

    public void execute(VoucherProgramFunctions type) {
        switch (type) {
            case create -> createVoucherByVoucherType();
            case voucherList -> printVoucherList();
            case blackList -> OutputConsole.printList(blackListService.getBlackList());
            case add -> createNewCustomer();
            case provide -> provideVoucherToCustomer();
            case manage -> {
                printCustomerVoucherList();
            }
        }
    }

    private void createVoucherByVoucherType() {
        new OutputConsole().printVoucherType();
        try {
            voucherService.createVoucher(UUID.randomUUID(),
                    IntUtils.toInt(inputConsole.inputString()),
                    IntUtils.toInt(inputConsole.inputStringWithPrintMessage("Type amount : ")));
        } catch (IllegalArgumentException e) {
            logger.info("error -> {}", e.getMessage());
            OutputConsole.printMessage(e.getMessage());
        }
    }

    private void printVoucherList() {
        VoucherMap voucherMap = voucherService.getVoucherList();
        if (voucherMap.isEmptyMap()) {
            OutputConsole.printMessage("voucher list is empty !!\n");
            return;
        }
        OutputConsole.printMessage(voucherMap.toString());
        voucherMap.printKeys();
    }

    private void createNewCustomer() {
        String name = inputConsole.inputStringWithPrintMessage("input customer name : ");
        String email = inputConsole.inputStringWithPrintMessage("input customer Email : ");
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
        customerService.createCustomer(customer);
    }

    private void provideVoucherToCustomer() {
        Vouchers vouchers = voucherService.getOwnableVoucherList();
        Customers customers = customerService.getAllCustomers();
        if (vouchers.isEmptyList() || customers.isEmptyList()) {
            return;
        }
        vouchers.printList();
        String voucherId = inputConsole.inputString();
        customers.printList();
        String customerId = inputConsole.inputString();

        Optional<Voucher> voucher = voucherService.provideVoucherToCustomer(voucherId, customerId);
        voucher.ifPresent(value ->
                OutputConsole.printMessage(MessageFormat.format("{0} is provided", value.getVoucherId())));
    }

    private void printCustomerVoucherList() {
        String customerEmail = OutputMessageInputString("input customer Email");
        VoucherMap voucherMap = voucherWalletService.getVoucherListByCustomerEmail(customerEmail);
        if (!voucherMap.isEmptyMap()) {
            voucherMap.printKeys();
            deleteVoucher(customerEmail);
        }
    }

    private void deleteVoucher(String email) {
        String inputString = OutputMessageInputString("Type D/d if you want delete");
        if (inputString.equalsIgnoreCase(DELETE_CHARACTER)) {
            String voucherId = OutputMessageInputString("Type voucherId");
            voucherService.deleteVoucher(UUID.fromString(voucherId), email);
        }
    }

    private String OutputMessageInputString(String message) {
        OutputConsole.printMessage(message);
        return inputConsole.inputString();
    }
}
