package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequest;
import org.prgrms.vouchermanager.exception.FailToDeleteException;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.exception.NotExistVoucherException;
import org.prgrms.vouchermanager.repository.customer.JdbcCustomerRepository;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.repository.wallet.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {
    private final JdbcWalletRepository walletRepository;
    private final JdbcCustomerRepository customerRepository;

    public WalletRequest createWallet(WalletRequest requestDto) {
        customerRepository.findByEmail(requestDto.getCustomerEmail()).orElseThrow(NotExistEmailException::new);
        walletRepository.findByVoucher(requestDto.getVoucher()).orElseThrow(NotExistVoucherException::new);
        return walletRepository.save(requestDto);
    }
    public Wallet findByEmail(String email) {
        return walletRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
    }

    public Optional<Wallet> deleteByEmail(String email) {
        customerRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        return walletRepository.deleteByEmail(email);
    }

    public Wallet findByVoucher(Voucher voucher) {
        return walletRepository.findByVoucher(voucher).orElseThrow(NotExistVoucherException::new);
    }


}
