package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    void registerVoucher(UUID customerId, UUID voucherId);
    List<Voucher> findVouchersByCustomerId(UUID customerId);
    void removeVoucher(UUID voucherId);
    Optional<UUID> findVoucherOwner(UUID voucherId);
    void deleteById(UUID voucherId);
    void deleteAll();
    void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest);
}
