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
import org.prgrms.kdt.util.IntUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class FunctionOperator {

    private BlackListService blackListService;
    private CustomerService customerService;
    private VoucherService voucherService;
    private VoucherWalletService voucherWalletService;
    private final static Logger logger = LoggerFactory.getLogger(VoucherProgramFunctions.class);
    private final static Input input = new InputConsole();
    private final static Output output = new OutputConsole();
    private final static String DELETE_CHARACTER = "D";

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
            case ("blackList") -> output.printList(blackListService.getBlackList());
            case ("add") -> createNewCustomer();
            case ("provide") -> provideVoucherToCustomer();
            case ("manage") -> {
                String email = printCustomerVoucherList();
                deleteCheck(email);
            }
        }
    }

    private void createVoucherByVoucherType() {
        Output output = new OutputConsole();
        output.printVoucherType();
        try {
            voucherService.createVoucher(UUID.randomUUID(),
                    IntUtils.toInt(input.inputString()),
                    IntUtils.toInt(input.inputStringWithPrintMessage("Type amount : ")));
        } catch (IllegalArgumentException e) {
            logger.info("error -> {}", e.getMessage());
            output.printMessage(e.getMessage());
        }
    }

    private void printVoucherList() {
        Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
        if (voucherList.isEmpty()) {
            output.printMessage("voucher list is empty !!\n");
            return;
        }
        for (Map.Entry<UUID, Voucher> entry : voucherList.entrySet()) {
            Voucher voucher = entry.getValue();
            System.out.println(voucher.toString());
        }
    }

    private void createNewCustomer() {
        String name = input.inputStringWithPrintMessage("input customer name : ");
        String email = input.inputStringWithPrintMessage("input customer Email : ");
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
        customerService.createCustomer(customer);
    }

    private void provideVoucherToCustomer() {
        List<Voucher> voucherList = voucherService.getOwnableVoucherList();
        String voucherId = outputListInputString(voucherList);

        List<Customer> customerList = customerService.getAllCustomers();
        String customerId = outputListInputString(customerList);

        //vouchers table update
        Optional<Voucher> voucher = voucherService.provideVoucherToCustomer(voucherId, customerId);
        voucher.ifPresent(value -> output.printMessage(MessageFormat.format("{} is provided", value.getVoucherId())));
    }

    private String printCustomerVoucherList() {
        String customerEmail = OutputMessageInputString("input customer Email");
        Optional<Map<UUID, Voucher>> voucherList = voucherWalletService.getVoucherListByCustomerEmail(customerEmail);
        voucherList.ifPresent(uuidVoucherMap -> new ArrayList<>(uuidVoucherMap.values()));
        return customerEmail;
    }

    private void deleteCheck(String email) {
        String inputString = OutputMessageInputString("Type D/d if you want delete");
        if(inputString.equalsIgnoreCase(DELETE_CHARACTER)) {
            String voucherId = OutputMessageInputString("Type voucherId");
            voucherService.deleteVoucher(UUID.fromString(voucherId), email);
        }
    }

    private String outputListInputString(List<?> list) {
        output.printList(list);
        return input.inputString();
    }

    private String OutputMessageInputString(String message) {
        output.printMessage(message);
        return input.inputString();
    }
}
