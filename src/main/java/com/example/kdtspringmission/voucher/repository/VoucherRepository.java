package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    UUID insert(Voucher voucher);

    Voucher findById(UUID id);

    List<Voucher> findAll();

    void clear();

}
