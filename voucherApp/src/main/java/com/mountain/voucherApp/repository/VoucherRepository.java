package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<VoucherEntity> findAll();
    VoucherEntity insert(VoucherEntity voucherEntity);
}
