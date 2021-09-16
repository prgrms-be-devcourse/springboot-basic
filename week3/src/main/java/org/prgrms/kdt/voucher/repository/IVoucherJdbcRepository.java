package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IVoucherJdbcRepository extends VoucherRepository{

    void deleteByVoucherId(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByVouchersTerm(LocalDateTime startDate, LocalDateTime endDate);

    int insertWalletIdToVoucher(UUID walletUUID, Voucher voucher);
}
