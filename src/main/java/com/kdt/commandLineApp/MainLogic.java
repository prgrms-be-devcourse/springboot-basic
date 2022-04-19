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
    private VoucherWalletService voucherWalletService;

    @Autowired
    public MainLogic(IO io, VoucherService voucherService, CustomerService customerService, VoucherWalletService voucherWalletService) {
        this.io = io;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.voucherWalletService = voucherWalletService;
    }

    public void printMainMenu() {
        io.print("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                "Type blacklist to list all blacklist custom info.\n"+
                "========================\n"+
                "Type give voucher to give voucher to custmer.\n"+
                "Type take voucher to take a voucher from customer.\n"+
                "Type customer list to see customer list with same voucher.\n"+
                "Type voucher list to see voucher list of customer.\n"
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

    public void giveVoucher() throws Exception {
        io.print("Type custmer id you want to give voucher to.");
        String customerId = io.get();
        io.print("Type voucher id you want to give to customer.");
        String voucherId = io.get();
        voucherWalletService.giveVoucherToCustomer(customerId, voucherId);
    }

    public void takeVoucher() throws Exception {
        io.print("Type custmer id you want to take voucher from.");
        String customerId = io.get();
        io.print("Type voucher id you want to take from customer.");
        String voucherId = io.get();
        voucherWalletService.deleteVoucherFromCustomer(customerId, voucherId);
    }

    public void getCustomerListWithSameVoucher() throws Exception {
        io.print("Type voucher id you want see customer list.");
        String voucherId = io.get();
        io.print(voucherWalletService.getCustomersWithVoucherId(voucherId));
    }

    public void getVoucherListOfCustomer() throws Exception {
        io.print("Type customer id you want see voucher list.");
        String customerId = io.get();
        io.print(voucherWalletService.getCustomerVouchers(customerId));
    }
}
