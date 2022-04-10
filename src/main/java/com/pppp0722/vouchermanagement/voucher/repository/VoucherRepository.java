package com.pppp0722.vouchermanagement.voucher.repository;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> getVoucherList();
}
