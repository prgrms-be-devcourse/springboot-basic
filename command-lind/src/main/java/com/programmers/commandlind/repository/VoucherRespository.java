package com.programmers.commandlind.repository;

import com.programmers.commandlind.entity.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRespository {
    Optional<Voucher> findById(UUID voucherId);

}
