package com.programmers.voucher.service;

import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Voucher assign(UUID voucherId, String email) {
        Voucher voucher = voucherRepository.findById(voucherId);
        Customer customer = customerRepository.findByEmail(email);
        voucher.setCustomer(customer);
        voucherRepository.assign(voucher);
        return voucher;
    }
}
