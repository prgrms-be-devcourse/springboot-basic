package com.example.demo.domain.voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> readVoucherList();
}
