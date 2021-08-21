package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;

public interface VoucherRepository {

    Long insert(Voucher voucher);

    Voucher findById(Long id);

    List<Voucher> findAll();

}
