package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    UUID insert(Voucher voucher);

    Voucher findById(UUID id);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

}
