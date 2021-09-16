package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.wallet.Wallet;
import org.prgrms.kdt.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public WalletService(WalletRepository walletRepository, CustomerService customerService, VoucherService voucherService) {
        this.walletRepository = walletRepository;
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @Transactional
    public Wallet assignVoucher(UUID customerId, UUID voucherId) {
        Wallet wallet = walletRepository.insert(customerId, voucherId);
        return wallet;
    }

    public Wallet findCustomerWallet(UUID customerId) {
        Wallet wallet = walletRepository.findByCustomerId(customerId);
        return wallet;
    }

    @Transactional
    public void deleteCustomerVoucher(UUID customerId, UUID voucherId) {
        walletRepository.delete(customerId, voucherId);
    }

    public List<Customer> findCustomersHavingVoucher(UUID voucherId) {
        List<Wallet> wallet = walletRepository.findByVoucherId(voucherId);
        List<Customer> customers = wallet.stream()
                .map(w -> w.getCustomerId())
                .map(customerId -> customerService.findById(customerId))
                .collect(Collectors.toList());

        return customers;
    }

    public List<Voucher> findVouchersNotAssignedToCustomer(UUID customerId) {
        Wallet wallet = walletRepository.findByCustomerId(customerId);
        Set<UUID> voucherIdSet = createVoucherIdSet(wallet);
        return allVouchers().stream()
                .filter(voucher -> isNotAssignedToCustomer(voucherIdSet, voucher))
                .collect(Collectors.toList());
    }

    public List<Customer> customersHavingWallet() {
        return walletRepository.findAllCustomers();
    }

    public List<Customer> allCustomers() {
        return customerService.customers();
    }

    public List<Voucher> allVouchers() {
        return voucherService.vouchers();
    }

    public Voucher findVoucher(UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    private HashSet<UUID> createVoucherIdSet(Wallet wallet) {
        return new HashSet<>(wallet.getVoucherIds());
    }

    private boolean isNotAssignedToCustomer(Set<UUID> voucherIdSet, Voucher voucher) {
        return !voucherIdSet.contains(voucher.getVoucherId());
    }

}
