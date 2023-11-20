package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.ApiWalletRequest;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.WalletRequest;
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
    private final JdbcVoucherRepository voucherRepository;

    public WalletRequest createWallet(WalletRequest requestDto) {
        customerRepository.findByEmail(requestDto.customerEmail()).orElseThrow(NotExistEmailException::new);
        walletRepository.findByVoucher(requestDto.voucher()).orElseThrow(NotExistVoucherException::new);
        return walletRepository.save(requestDto);
    }
    public WalletRequest findByEmail(String email) {
        Wallet wallet = walletRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        Voucher voucher = voucherRepository.findByID(wallet.getVoucherId()).orElseThrow(NotExistVoucherException::new);
        return new WalletRequest(wallet.getCustomerEmail(), voucher);
    }

    public WalletRequest deleteByEmail(String email) {
        customerRepository.findByEmail(email).orElseThrow(NotExistEmailException::new);
        Optional<Wallet> wallet = walletRepository.deleteByEmail(email);
        Voucher voucher = voucherRepository.findByID(wallet.get().getVoucherId()).orElseThrow(NotExistVoucherException::new);
        return new WalletRequest(wallet.get().getCustomerEmail(), voucher);
    }

    public WalletRequest findByVoucher(Voucher voucher) {
        Wallet wallet = walletRepository.findByVoucher(voucher).orElseThrow(NotExistVoucherException::new);
        Voucher findVoucher = voucherRepository.findByID(voucher.getVoucherId()).orElseThrow(NotExistVoucherException::new);
        return new WalletRequest(wallet.getCustomerEmail(), findVoucher);

    }


}
