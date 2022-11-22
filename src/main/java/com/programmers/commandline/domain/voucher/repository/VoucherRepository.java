package com.programmers.commandline.domain.voucher.repository;

import com.programmers.commandline.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    String save(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(String voucherId);
}
