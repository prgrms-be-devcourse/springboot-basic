package com.example.voucher.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import com.example.voucher.domain.Voucher;

@Repository
public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

    Voucher findById(UUID voucherID);

}
