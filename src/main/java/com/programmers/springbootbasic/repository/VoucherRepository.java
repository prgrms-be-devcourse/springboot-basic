package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.VoucherDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    VoucherDTO insert(VoucherDTO voucherDTO);

    Optional<VoucherDTO> findById(UUID voucherId);

    List<VoucherDTO> findAvailableVouchers();

    List<VoucherDTO> findAll();

    void deleteById(UUID voucherId);

    void deleteAll();

}
