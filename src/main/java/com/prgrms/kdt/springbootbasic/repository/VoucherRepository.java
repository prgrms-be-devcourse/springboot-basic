package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    public Voucher findById(UUID voucherId);

    public Voucher saveVoucher(Voucher voucher);

    public List<Voucher> getAllVouchers();
}
