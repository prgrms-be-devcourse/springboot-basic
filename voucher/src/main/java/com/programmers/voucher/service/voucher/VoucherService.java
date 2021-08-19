package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher create(String name, Voucher.type type);

    List<Voucher> listAll();
}
