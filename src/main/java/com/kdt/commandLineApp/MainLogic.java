package com.kdt.commandLineApp;

import com.kdt.commandLineApp.customer.CustomerService;
import com.kdt.commandLineApp.io.IO;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainLogic {
    private IO io;
    private VoucherService voucherService;
    private CustomerService customerService;

    @Autowired
    public MainLogic(IO io, VoucherService voucherService, CustomerService customerService) {
        this.io = io;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void printMainMenu() {
        io.print("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                "Type blacklist to list all blacklist custom info"
        );
    }

    public Command getCommand() throws Exception {
        return Command.fromString(io.get());
    }

    public void createVoucher() throws Exception {
        io.print("Type voucher type(fixed or percent) and amount");
        String voucherType = io.get();
        int amount = Integer.parseInt(io.get());
        voucherService.addVoucher(voucherType, amount);
    }

    public void showVouchers() {
        io.print(voucherService.getVouchers());
    }

    public void showBlackList() throws Exception {
        io.print(customerService.getCustomers());
    }
}
