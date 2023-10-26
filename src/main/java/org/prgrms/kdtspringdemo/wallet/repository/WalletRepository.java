package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;

import java.util.UUID;
import java.util.List;

public interface WalletRepository {
    Wallet insert(Wallet wallet);
    List<Voucher> findAllVoucherByCustomerId(UUID customerId);
    void deleteVoucherByVoucherId(UUID voucherId);
    void findCustomerByVoucherId(UUID voucherId);
    
}
