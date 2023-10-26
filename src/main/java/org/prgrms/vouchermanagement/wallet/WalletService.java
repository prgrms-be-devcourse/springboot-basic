package org.prgrms.vouchermanagement.wallet;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void create(WalletCreateInfo walletCreateInfo) {
        walletRepository.create(walletCreateInfo);
    }


    public List<Voucher> findVouchers(UUID customerId) {
        return walletRepository.findVouchers(customerId);
    }

    public void delete(UUID customerId) {
        walletRepository.delete(customerId);
    }

    public Customer findCustomer(UUID voucherId) {
        return walletRepository.findCustomer(voucherId);
    }
}
