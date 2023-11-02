package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    UUID save(Voucher voucher);

    void saveAll(List<Voucher> vouchers);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll(GetVouchersRequestDto request);

    void update(Voucher voucher);

    void deleteById(UUID id);

    void deleteAll();
}
