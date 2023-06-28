package org.prgms.voucher.repository;

import org.prgms.voucher.dto.VoucherResponseDto;
import org.prgms.voucher.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<VoucherResponseDto> findAll();

    Voucher save(Voucher voucher);

    Optional<VoucherResponseDto> findById(UUID id);
}
