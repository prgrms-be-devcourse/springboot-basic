package org.prgrms.springbasic.repository.voucher;

import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findByVoucherId(UUID voucherId);

    Optional<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findVouchers();

    List<Wallet> findWallets();

    int countData();

    Voucher update(Voucher voucher);

    void deleteByVoucherId(UUID voucherId);

    void deleteByCustomerId(UUID customerId);

    void deleteVouchers();
}