package com.program.commandLine.service;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.repository.VoucherWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public Voucher assignCustomer(Voucher voucher, Customer customer) {
        // try catch
        voucherWalletRepository.createWallet(voucher.getVoucherId(),customer.getCustomerId());
        return voucher;
    }
    public Voucher retrieveVoucher(Voucher voucher) {
        // try catch
        voucherWalletRepository.deleteWallet(voucher.getVoucherId());
        return voucher;
    }


    public List<Voucher> getNotAssignedVoucher() {
        return voucherWalletRepository.findNotIncludeWallet();
    }


    public UUID getAssignedCustomer(Voucher voucher) {
        return voucherWalletRepository.findCustomerWalletByVoucher(voucher.getVoucherId());
    }

}
