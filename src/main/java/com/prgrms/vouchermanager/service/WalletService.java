package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgrms.vouchermanager.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.customer.CustomerResponse.CustomerDetailResponse;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.VoucherDetailResponse;
import static com.prgrms.vouchermanager.dto.wallet.WalletRequest.WalletDetailRequest;
import static com.prgrms.vouchermanager.dto.wallet.WalletResponse.WalletDetailResponse;
import static com.prgrms.vouchermanager.dto.wallet.WalletResponse.toDetailWallet;
import static com.prgrms.vouchermanager.service.VoucherService.getVoucherDetailResponses;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public WalletDetailResponse create(WalletDetailRequest request) {
        Wallet wallet = new Wallet(request.voucherId(), request.customerId());
        Wallet createdWallet = walletRepository.create(wallet);
        return toDetailWallet(createdWallet);
    }

    public List<WalletDetailResponse> findAll() {
        List<Wallet> wallets = walletRepository.findAll();
        return getWalletDetailResponses(wallets);
    }

    private static List<WalletDetailResponse> getWalletDetailResponses(List<Wallet> list) {
        return list.stream()
                .map(wallet ->
                        WalletDetailResponse.builder()
                                .walletId(wallet.getWalletId())
                                .voucherId(wallet.getVoucherId())
                                .customerId(wallet.getCustomerId())
                                .build())
                .toList();
    }

    private static List<CustomerDetailResponse> getCustomerDetailResponses(List<Customer> list) {
        return list.stream()
                .map(customer ->
                        CustomerDetailResponse.builder()
                                .customerId(customer.getId())
                                .name(customer.getName())
                                .yearOfBirth(customer.getYearOfBirth())
                                .isBlacklist(customer.isBlacklist())
                                .build())
                .toList();
    }

    public WalletDetailResponse findById(UUID walletId) {
        return toDetailWallet(walletRepository.findById(walletId));
    }

    public List<VoucherDetailResponse> findByCustomerId(UUID id) {
        List<Wallet> walletList = walletRepository.findByCustomerId(id);
        List<Voucher> voucherList = new ArrayList<>();

        walletList.forEach(wallet -> {
            UUID voucherId = wallet.getVoucherId();
            voucherList.add(voucherRepository.findById(voucherId));
        });
        return getVoucherDetailResponses(voucherList);
    }

    public List<CustomerDetailResponse> findByVoucherId(UUID id) {
        List<Wallet> walletList = walletRepository.findByVoucherId(id);
        List<Customer> customerList = new ArrayList<>();

        walletList.forEach(wallet -> {
            UUID customerId = wallet.getCustomerId();
            customerList.add(customerRepository.findById(customerId));
        });
        return getCustomerDetailResponses(customerList);
    }

    public int delete(UUID walletId) {
        return walletRepository.delete(walletId);
    }
}
