package com.weeklyMission.voucher.repository;

import com.weeklyMission.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(String id);

    void deleteById(String id);
}
