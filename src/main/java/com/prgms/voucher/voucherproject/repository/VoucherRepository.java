package com.prgms.voucher.voucherproject.repository;

import com.prgms.voucher.voucherproject.domain.Voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId); // voucher가 존재하지 않을 수 있음을 Optional로
    void save(Voucher voucher);

    ArrayList<Voucher> findAll();


}
