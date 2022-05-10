package com.kdt.commandLineApp.console;

import com.kdt.commandLineApp.io.IO;
import com.kdt.commandLineApp.service.voucherWallet.VoucherWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherWalletMainLogic {
    private IO io;
    private VoucherWalletService voucherWalletService;

    @Autowired
    public VoucherWalletMainLogic(IO io, VoucherWalletService voucherWalletService) {
        this.io = io;
        this.voucherWalletService = voucherWalletService;
    }

    public void giveVoucher() throws Exception {
        io.print("Type custmer id you want to give voucher to.");
        long customerId = Long.parseLong(io.get());
        io.print("Type voucher id you want to give to customer.");
        long voucherId = Long.parseLong(io.get());
        voucherWalletService.giveVoucherToCustomer(customerId, voucherId);
    }

    public void takeVoucher() throws Exception {
        io.print("Type custmer id you want to take voucher from.");
        long customerId = Long.parseLong(io.get());
        io.print("Type voucher id you want to take from customer.");
        long voucherId = Long.parseLong(io.get());
        voucherWalletService.deleteVoucherFromCustomer(customerId, voucherId);
    }

    public void getCustomerListWithSameVoucher() throws Exception {
        io.print("Type voucher id you want see customer list.");
        long voucherId = Long.parseLong(io.get());
        io.print(voucherWalletService.getCustomersWithVoucherId(voucherId));
    }

    public void getVoucherListOfCustomer() throws Exception {
        io.print("Type customer id you want see voucher list.");
        long customerId = Long.parseLong(io.get());
        io.print(voucherWalletService.getCustomerVouchers(customerId));
    }
}
