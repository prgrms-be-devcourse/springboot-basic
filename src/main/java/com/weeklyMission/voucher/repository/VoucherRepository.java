package com.weeklyMission.voucher.repository;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(String id);

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByIds(List<String> ids);

    void deleteById(String id);
}
