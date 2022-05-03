package org.prgrms.springbasic.repository.voucher;

import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findByVoucherId(UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByCreatedPeriod(String from, String to);

    List<Voucher> findVouchers();

    List<Wallet> findWallets();

    int countVouchers();

    Voucher update(Voucher voucher);

    boolean deleteByVoucherId(UUID voucherId);

    boolean deleteByCustomerId(UUID customerId);

    void deleteVouchers();
}