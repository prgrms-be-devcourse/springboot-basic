package com.example.kdtspringmission;

import java.util.List;

public interface VoucherRepository {

    Long insert(Voucher voucher);

    Voucher findById(Long id);

    List<Voucher> findAll();

}
