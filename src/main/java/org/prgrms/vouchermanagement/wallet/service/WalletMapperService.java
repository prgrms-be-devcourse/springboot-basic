package org.prgrms.vouchermanagement.wallet.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.exception.LoadFailException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.wallet.domain.WalletMapper;
import org.prgrms.vouchermanagement.wallet.repository.WalletMapperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletMapperService {

    private final WalletMapperRepository walletMapperRepository;

    public WalletMapperService(WalletMapperRepository walletMapperRepository) {
        this.walletMapperRepository = walletMapperRepository;
    }

    public WalletCreateInfo create(WalletCreateInfo walletCreateInfo) {
        walletMapperRepository.create(walletCreateInfo);
        return walletCreateInfo;
    }

    public List<Voucher> findVouchers(UUID customerId) {
        return walletMapperRepository.findVouchers(customerId);
    }

    public UUID delete(UUID customerId) {
        walletMapperRepository.delete(customerId);
        return customerId;
    }

    public Customer findCustomer(UUID voucherId) {
        return walletMapperRepository
                .findCustomer(voucherId)
                .orElseThrow(() -> new LoadFailException("찾을 수 없습니다."));
    }

    public List<WalletMapper> findAll() {
        return walletMapperRepository.findAll();
    }
}
