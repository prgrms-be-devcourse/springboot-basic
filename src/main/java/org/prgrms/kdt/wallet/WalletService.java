package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.prgrms.kdt.customer.CustomerMessage.EXCEPTION_NOT_EXIST_CUSTOMER;
import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_NOT_EXIST_VOUCHER;

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

    public String addWallet(String customerId, String voucherId) {
        validateCustomerExist(customerId);
        validateVoucherExist(voucherId);

        Wallet wallet = new Wallet(UUID.randomUUID().toString(), customerId, voucherId);
        return walletRepository.save(wallet).getWalletId();
    }

    public boolean deleteByCustomerId(String customerId) {
        walletRepository.deleteByCustomerId(customerId);
        return true;
    }

    private void validateCustomerExist(String customerId) {
        customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(() -> new RuntimeException(EXCEPTION_NOT_EXIST_CUSTOMER.getMessage()));
    }

    private void validateVoucherExist(String voucherId) {
        voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(() -> new RuntimeException(EXCEPTION_NOT_EXIST_VOUCHER.getMessage()));
    }
}
