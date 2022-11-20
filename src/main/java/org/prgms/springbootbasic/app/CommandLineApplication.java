package org.prgms.springbootbasic.app;

import org.prgms.springbootbasic.cli.Command;
import org.prgms.springbootbasic.cli.CommandLine;
import org.prgms.springbootbasic.controller.CustomerController;
import org.prgms.springbootbasic.controller.VoucherController;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class CommandLineApplication {

    private final CommandLine commandLine;
    private final CustomerController customerController;
    private final VoucherController voucherController;

    public CommandLineApplication(CommandLine commandLine, CustomerController customerController, VoucherController voucherController) {
        this.commandLine = commandLine;
        this.customerController = customerController;
        this.voucherController = voucherController;
    }

    public void run(boolean isAppRunning) {

        while (isAppRunning) {
            Command command = Command.findCommand(commandLine.getMainCommand());

            switch (command) {
                case EXIT -> isAppRunning = !this.commandLine.stopCommandLine();
                case CREATE_VOUCHER -> voucherController.createVoucher(commandLine.getVoucherCommand());
                case CREATE_CUSTOMER -> customerController.createCustomer(commandLine.getCustomerCommand());
                case LIST_VOUCHER -> commandLine.printList(voucherController.voucherList());
                case LIST_CUSTOMER -> commandLine.printList(customerController.customerList());
                case MANAGE_VOUCHER -> manageVoucher();
                case FIND_CUSTOMER -> commandLine.printList(customerController.findCustomers(commandLine.getVoucherCommand("to find customer information")));
                case BLACKLIST -> commandLine.printList(customerController.blacklistedCustomerList());
            }
        }
    }

    private void manageVoucher() {
        UUID customerId = commandLine.getManageVoucherCommand();

        Customer customer = customerController.findOneCustomer(customerId).orElseThrow();
        customerController.updateLastLoginTime(customer);

        List<Voucher> voucherList = voucherController.findVouchers(customerId);
        commandLine.printList(voucherList);

        if(!voucherList.isEmpty()) {
            boolean isDelete = commandLine.getYesOrNoCommand("if you want to delete all vouchers belonging to the customer");
            if(isDelete) {
                voucherController.deleteVouchers(customerId);
            }
        }

        boolean isAllocate = commandLine.getYesOrNoCommand("if you want to allocate a voucher to the customer");
        if(isAllocate) {
            commandLine.printList(voucherController.voucherList());

            Voucher voucher = voucherController.findOneVoucher(commandLine.getVoucherCommand("to allocate voucher to the customer")).orElseThrow();
            voucher.setCustomerId(customerId);

            voucherController.allocateOneVoucher(voucher);
        }
    }


}
