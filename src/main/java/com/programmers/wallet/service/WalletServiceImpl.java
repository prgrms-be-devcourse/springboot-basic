package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.customer.service.CustomerService;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.message.ErrorMessage.*;

@Service
@Transactional(readOnly = true)
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerService customerService;

    public WalletServiceImpl(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository, CustomerService customerService) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Customer assignVoucher(UUID customerId, UUID voucherId) {
        Customer customer = getCustomer(customerId);

        Voucher voucher = getVoucher(voucherId);

        walletRepository.assignVoucher(customer, voucher);
        customer.addVoucher(voucher);

        return customer;
    }

    private Voucher getVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(VOUCHER_ID_NOT_FOUND.getMessage()));
        return voucher;
    }

    private Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Voucher> searchVouchersByCustomerId(UUID customerId) {
        return walletRepository.findVouchersByCustomerId(customerId);
    }

    @Override
    public Customer searchCustomerByVoucherId(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_ERROR.getMessage()));
    }

    @Override
    @Transactional
    public void removeCustomerVoucher(UUID customerId, UUID voucherId) {
        Customer customer = getCustomer(customerId);

        Voucher findVoucher = getVoucher(voucherId);

        customer.removeVoucher(findVoucher);

        walletRepository.deleteCustomerVoucher(customerId, voucherId);
    }
}
