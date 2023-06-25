package com.prgrms.springbootbasic.repository;

import com.prgrms.springbootbasic.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> getAllVouchersList();
}
