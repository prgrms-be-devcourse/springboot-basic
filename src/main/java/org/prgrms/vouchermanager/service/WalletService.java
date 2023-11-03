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
    private final JdbcVoucherRepository voucherRepository;
    private final JdbcWalletRepository walletRepository;
    private final JdbcCustomerRepository customerRepository;

    public WalletRequest createWallet(WalletRequest requestDto) {
        String customerEmail = requestDto.getCustomerEmail();
        customerRepository.findByEmail(customerEmail).orElseThrow(NotExistEmailException::new);
        return walletRepository.save(requestDto);
    }
//    public Optional<Wallet> findByEmail(String email) {
//        return Optional.ofNullable(walletRepository.findByEmail(email).orElseThrow(NotExistEmailException::new));
//    }

    public Wallet findByEmail(String email) {
        Wallet wallet = walletRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        return wallet;
    }

    public Wallet deleteByEmail(String email) {
        customerRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        return walletRepository.deleteByEmail(email).orElseThrow(FailToDeleteException::new);
    }

    public Wallet findByVoucher(Voucher voucher) {
        return walletRepository.findByVoucher(voucher).orElseThrow(NotExistVoucherException::new);
    }


}
