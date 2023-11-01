package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    void deleteById(UUID voucherId);
    void deleteAll();
    void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest);
    List<Voucher> findVoucherByCondition(VoucherCondition condition);
}
