package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.exception.NotExistVoucherException;
import org.prgrms.vouchermanager.repository.customer.JdbcCustomerRepository;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.repository.wallet.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {
    private final JdbcVoucherRepository voucherRepository;
    private final JdbcWalletRepository walletRepository;
    private final JdbcCustomerRepository customerRepository;

    public WalletRequestDto createWallet(WalletRequestDto requestDto){
        String customerEmail = requestDto.getCustomerEmail();
        requestDto.getVoucher().getVoucherId();
        customerRepository.findByEmail(customerEmail).orElseThrow(NotExistEmailException::new);
        return walletRepository.save(requestDto);
    }
    public Optional<Wallet> findByEmail(String email){
        return walletRepository.findByEmail(email);
    }

    public Optional<Wallet> deleteByEmail(String email) {
        customerRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        return walletRepository.deleteByEmail(email);
    }
    public Optional<Wallet> findByVoucher(Voucher voucher) {
        voucherRepository.findByID(voucher.getVoucherId()).orElseThrow(NotExistVoucherException::new);
        return walletRepository.findByVoucher(voucher);
    }


}
