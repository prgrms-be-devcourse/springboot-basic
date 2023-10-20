package com.programmers.springbootbasic.domain.wallet.service;

import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import com.programmers.springbootbasic.domain.wallet.dto.WalletRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.programmers.springbootbasic.domain.customer.exception.ErrorMsg.customerNotFound;
import static com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.voucherNotFound;
import static com.programmers.springbootbasic.domain.wallet.exception.ErrorMsg.walletAlreadyExist;
import static com.programmers.springbootbasic.domain.wallet.exception.ErrorMsg.walletNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public Wallet createWallet(WalletRequestDto walletRequestDto) {
        Customer customer = findCustomer(walletRequestDto.getEmail());
        Voucher voucher = findVoucher(walletRequestDto.getVoucherId());
        if (walletRepository.findByValues(customer.getEmail(), voucher.getVoucherId()).isPresent()) {
            log.warn(walletAlreadyExist.getMessage());
            throw new RuntimeException(walletAlreadyExist.getMessage());
        }
        Wallet wallet = Wallet.builder()
                .email(customer.getEmail())
                .voucherId(voucher.getVoucherId())
                .build();
        return walletRepository.save(wallet);
    }

    public List<Wallet> findWalletsByCustomerEmail(WalletRequestDto walletRequestDto) {
        Customer customer = findCustomer(walletRequestDto.getEmail());
        return walletRepository.findByCustomerEmail(customer.getEmail());
    }

    public List<Wallet> findWalletsByVoucherId(WalletRequestDto walletRequestDto) {
        Voucher voucher = findVoucher(walletRequestDto.getVoucherId());
        return walletRepository.findByVoucherId(voucher.getVoucherId());
    }

    public void deleteWallet(WalletRequestDto walletRequestDto) {
        Customer customer = findCustomer(walletRequestDto.getEmail());
        Voucher voucher = findVoucher(walletRequestDto.getVoucherId());
        Wallet wallet = walletRepository.findByValues(customer.getEmail(), voucher.getVoucherId()).orElseThrow(() -> {
            log.warn(walletNotFound.getMessage());
            return new RuntimeException(walletNotFound.getMessage());
        });
        walletRepository.delete(wallet);
    }

    private Customer findCustomer(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> {
            log.warn(customerNotFound.getMessage());
            return new RuntimeException(customerNotFound.getMessage());
        });
    }

    private Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() -> {
            log.warn(voucherNotFound.getMessage());
            return new RuntimeException(voucherNotFound.getMessage());
        });
    }
}
