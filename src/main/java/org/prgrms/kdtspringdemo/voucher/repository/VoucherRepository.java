package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    void update(UUID voucherId, VoucherRequestDto voucherRequestDto);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    List<Voucher> findByPolicy(String policy);
    List<Voucher> findUnallocatedVoucher();
    void deleteById(UUID voucherId);
    void deleteAll();
}
